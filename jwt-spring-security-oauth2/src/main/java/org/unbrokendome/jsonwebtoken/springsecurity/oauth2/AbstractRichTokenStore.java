package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;
import java.util.Collections;


/**
 * Base class for {@link TokenStore} implementations that deal with "rich" tokens, i.e. tokens that contain all
 * the authentication information in themselves.
 *
 * <p>
 * This class implements all methods that deal with storage, retrieval and removal as no-ops.
 */
public abstract class AbstractRichTokenStore implements TokenStore {

    @Override
    public final OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;
    }


    @Override
    public final void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    }


    @Override
    public final void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
    }


    @Override
    public final void removeAccessToken(OAuth2AccessToken token) {
    }


    @Override
    public final void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
    }


    @Override
    public final void removeRefreshToken(OAuth2RefreshToken token) {
    }


    @Override
    public final Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return Collections.emptySet();
    }


    @Override
    public final Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return Collections.emptySet();
    }
}
