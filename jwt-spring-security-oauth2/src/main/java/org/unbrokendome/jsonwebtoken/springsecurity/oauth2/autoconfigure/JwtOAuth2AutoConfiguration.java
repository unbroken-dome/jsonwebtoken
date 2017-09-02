package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.method.OAuth2MethodSecurityConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;
import org.unbrokendome.jsonwebtoken.spring.autoconfigure.JwtAutoConfiguration;
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.JwtAccessTokenConverter;
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.JwtTokenEnhancer;
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.JwtTokenStore;
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.NullTokenStore;

import java.time.Clock;


@Configuration
@AutoConfigureAfter(JwtAutoConfiguration.class)
@Import({ OAuth2AuthorizationServerConfiguration.class,
        OAuth2ResourceServerConfiguration.class,
        OAuth2MethodSecurityConfiguration.class,
        OAuth2ClientConfiguration.class })
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class JwtOAuth2AutoConfiguration {

    private final Clock clock;
    private final OAuth2ClientProperties credentials;


    public JwtOAuth2AutoConfiguration(ObjectProvider<Clock> clock, OAuth2ClientProperties credentials) {
        Clock injectedClock = clock.getIfAvailable();
        this.clock = injectedClock != null ? injectedClock : Clock.systemDefaultZone();
        this.credentials = credentials;
    }


    @Bean
    @ConditionalOnBean(JwtDecodingProcessor.class)
    @ConditionalOnMissingBean(TokenStore.class)
    public JwtTokenStore jwtTokenStore(JwtDecodingProcessor jwtProcessor,
                                       ObjectProvider<ApprovalStore> approvalStore) {
        if (clock != null) {
            return new JwtTokenStore(jwtProcessor, approvalStore.getIfAvailable(), clock);
        } else {
            return new JwtTokenStore(jwtProcessor, approvalStore.getIfAvailable());
        }
    }


    @Bean
    @ConditionalOnBean(JwtEncodingProcessor.class)
    @ConditionalOnMissingBean({ JwtDecodingProcessor.class, TokenStore.class })
    public NullTokenStore nullTokenStore() {
        return new NullTokenStore();
    }


    @Bean
    @ConditionalOnBean(JwtEncodingProcessor.class)
    @ConditionalOnMissingBean(TokenEnhancer.class)
    public JwtTokenEnhancer jwtTokenEnhancer(JwtEncodingProcessor jwtProcessor) {
        return new JwtTokenEnhancer(jwtProcessor, clock);
    }


    @Bean
    @ConditionalOnMissingBean(AccessTokenConverter.class)
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new JwtAccessTokenConverter();
    }


    @Bean
    public ResourceServerProperties resourceServerProperties() {
        return new ResourceServerProperties(credentials.getClientId(), credentials.getClientSecret());
    }


    @Configuration
    @ConditionalOnClass(EnableAuthorizationServer.class)
    @ConditionalOnMissingBean(AuthorizationServerConfigurer.class)
    @ConditionalOnBean({ AuthorizationServerEndpointsConfiguration.class, JwtEncodingProcessor.class })
    static class AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {

        private final TokenEnhancer tokenEnhancer;


        public AuthorizationServerConfiguration(BaseClientDetails details, AuthenticationManager authenticationManager,
                                                ObjectProvider<TokenStore> tokenStore,
                                                ObjectProvider<AccessTokenConverter> tokenConverter,
                                                ObjectProvider<TokenEnhancer> tokenEnhancer,
                                                AuthorizationServerProperties properties) {
            super(details, authenticationManager, tokenStore, tokenConverter, properties);
            this.tokenEnhancer = tokenEnhancer.getIfAvailable();
        }


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            super.configure(endpoints);
            if (tokenEnhancer != null) {
                endpoints.tokenEnhancer(tokenEnhancer);
            }
        }
    }
}
