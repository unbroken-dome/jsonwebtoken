package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.validation.Errors;


public class JwtResourceServerProperties extends ResourceServerProperties {

    public JwtResourceServerProperties(ResourceServerProperties properties) {
        super(properties.getClientId(), properties.getClientSecret());
        setServiceId(properties.getServiceId());
        setId(properties.getId());
        setUserInfoUri(properties.getUserInfoUri());
        setTokenInfoUri(properties.getTokenInfoUri());
        setPreferTokenInfo(properties.isPreferTokenInfo());
        setTokenType(properties.getTokenType());
        setJwt(properties.getJwt());
        setJwk(properties.getJwk());
    }

    public JwtResourceServerProperties(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }


    @Override
    public void validate(Object target, Errors errors) {
    }
}
