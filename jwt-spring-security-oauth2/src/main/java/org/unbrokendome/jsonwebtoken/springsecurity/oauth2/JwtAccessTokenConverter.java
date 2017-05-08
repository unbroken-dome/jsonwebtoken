package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;

import java.util.Map;


public class JwtAccessTokenConverter implements AccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        return null;
    }


    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return null;
    }


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return null;
    }
}
