package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.unbrokendome.jsonwebtoken.Claims;

import javax.annotation.Nonnull;


public abstract class AbstractJwtOAuth2RefreshToken implements JwtOAuth2RefreshToken {

    private final String tokenValue;
    private final Claims claims;


    protected AbstractJwtOAuth2RefreshToken(String tokenValue, Claims claims) {
        this.tokenValue = tokenValue;
        this.claims = claims;
    }


    @Override
    public String getValue() {
        return tokenValue;
    }


    @Override
    @Nonnull
    public Claims getClaims() {
        return claims;
    }
}
