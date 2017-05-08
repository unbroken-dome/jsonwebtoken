package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
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
@AutoConfigureBefore(OAuth2AutoConfiguration.class)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class JwtOAuth2AutoConfiguration {

    @Bean
    @ConditionalOnBean(JwtDecodingProcessor.class)
    @ConditionalOnMissingBean(TokenStore.class)
    public JwtTokenStore jwtTokenStore(JwtDecodingProcessor jwtProcessor,
                                       ObjectProvider<ApprovalStore> approvalStore,
                                       ObjectProvider<Clock> clock) {
        return new JwtTokenStore(jwtProcessor,
                approvalStore.getIfAvailable(),
                getClockOrDefault(clock));
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
    public JwtTokenEnhancer jwtTokenEnhancer(JwtEncodingProcessor jwtProcessor,
                                             ObjectProvider<Clock> clock) {
        return new JwtTokenEnhancer(jwtProcessor,
                getClockOrDefault(clock));
    }


    @Bean
    @ConditionalOnMissingBean(AccessTokenConverter.class)
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new JwtAccessTokenConverter();
    }


    private Clock getClockOrDefault(ObjectProvider<Clock> clockProvider) {
        Clock clock = clockProvider.getIfAvailable();
        if (clock != null) {
            return clock;
        }
        return Clock.systemDefaultZone();
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


    @Configuration
    @ConditionalOnClass({ EnableResourceServer.class, SecurityProperties.class })
    @ConditionalOnWebApplication
    @ConditionalOnBean({ ResourceServerConfiguration.class, JwtDecodingProcessor.class })
    static class ResourceServerConfiguration {

        @Bean
        @ConditionalOnMissingBean(ResourceServerTokenServices.class)
        public ResourceServerTokenServices resourceServerTokenServices(TokenStore tokenStore) {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(tokenStore);
            return tokenServices;
        }
    }


    @Bean
    public static ResourceServerPropertiesPostProcessor resourceServerPropertiesPostProcessor(OAuth2ClientProperties credentials) {
        return new ResourceServerPropertiesPostProcessor(credentials);
    }
}
