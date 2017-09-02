package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;


@Configuration
@ConditionalOnClass({ EnableResourceServer.class, SecurityProperties.class })
@ConditionalOnWebApplication
@ConditionalOnBean({ ResourceServerConfiguration.class, JwtDecodingProcessor.class })
public class OAuth2ResourceServerConfiguration {

    private final ResourceServerProperties resourceServerProperties;


    public OAuth2ResourceServerConfiguration(ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
    }


    @Bean
    @ConditionalOnMissingBean({ AuthorizationServerSecurityConfiguration.class, ResourceServerTokenServices.class })
    public ResourceServerTokenServices resourceServerTokenServices(TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }


    @Bean
    @ConditionalOnMissingBean(ResourceServerConfigurer.class)
    public ResourceServerConfigurer resourceServer() {
        return new ResourceSecurityConfigurer(this.resourceServerProperties);
    }

    @Bean
    public static ResourceServerFilterChainOrderProcessor resourceServerFilterChainOrderProcessor(
            ResourceServerProperties properties) {
        return new ResourceServerFilterChainOrderProcessor(properties);
    }

    protected static class ResourceSecurityConfigurer
            extends ResourceServerConfigurerAdapter {

        private final ResourceServerProperties resource;


        public ResourceSecurityConfigurer(ResourceServerProperties resource) {
            this.resource = resource;
        }


        @Override
        public void configure(ResourceServerSecurityConfigurer resources)
                throws Exception {
            resources.resourceId(this.resource.getResourceId());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated();
        }

    }

    private static final class ResourceServerFilterChainOrderProcessor
            implements BeanPostProcessor, ApplicationContextAware {

        private final ResourceServerProperties properties;

        private ApplicationContext context;

        private ResourceServerFilterChainOrderProcessor(ResourceServerProperties properties) {
            this.properties = properties;
        }

        @Override
        public void setApplicationContext(ApplicationContext context)
                throws BeansException {
            this.context = context;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName)
                throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName)
                throws BeansException {
            if (bean instanceof ResourceServerConfiguration) {
                if (this.context.getBeanNamesForType(ResourceServerConfiguration.class,
                        false, false).length == 1) {
                    ResourceServerConfiguration config = (ResourceServerConfiguration) bean;
                    config.setOrder(this.properties.getFilterOrder());
                }
            }
            return bean;
        }

    }
}
