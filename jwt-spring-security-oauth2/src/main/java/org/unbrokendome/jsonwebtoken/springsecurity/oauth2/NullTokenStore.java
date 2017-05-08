package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;


/**
 * Dummy implementation of {@link TokenStore} that returns {@code null} from all methods.
 *
 * <p>
 * It is intended for use in an authorization server that only issues tokens but never needs to decode them.
 */
public final class NullTokenStore extends AbstractRichTokenStore {

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return null;
    }


    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return null;
    }


    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return null;
    }


    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return null;
    }


    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }
}
