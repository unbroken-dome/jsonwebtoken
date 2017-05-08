package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.core.PriorityOrdered;


final class ResourceServerPropertiesPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements PriorityOrdered {

    private final OAuth2ClientProperties credentials;


    ResourceServerPropertiesPostProcessor(OAuth2ClientProperties credentials) {
        this.credentials = credentials;
    }


    @Override
    public int getOrder() {
        return 0;
    }



    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass == ResourceServerProperties.class) {
            return new JwtResourceServerProperties(credentials.getClientId(), credentials.getClientSecret());
        }
        return super.postProcessBeforeInstantiation(beanClass, beanName);
    }
}
