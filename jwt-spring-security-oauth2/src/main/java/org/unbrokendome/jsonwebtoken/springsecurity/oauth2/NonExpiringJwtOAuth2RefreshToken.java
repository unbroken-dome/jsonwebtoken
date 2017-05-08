package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.unbrokendome.jsonwebtoken.Claims;


public final class NonExpiringJwtOAuth2RefreshToken
        extends AbstractJwtOAuth2RefreshToken {

    public NonExpiringJwtOAuth2RefreshToken(String tokenValue, Claims claims) {
        super(tokenValue, claims);
    }
}
