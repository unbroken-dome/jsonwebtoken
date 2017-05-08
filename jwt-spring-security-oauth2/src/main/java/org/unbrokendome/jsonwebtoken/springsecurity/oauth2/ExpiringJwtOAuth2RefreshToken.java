package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.unbrokendome.jsonwebtoken.Claims;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Date;


public final class ExpiringJwtOAuth2RefreshToken
        extends AbstractJwtOAuth2RefreshToken
        implements ExpiringOAuth2RefreshToken {

    public ExpiringJwtOAuth2RefreshToken(String tokenValue, Claims claims) {
        super(tokenValue, claims);
    }


    @Override
    @Nullable
    public Date getExpiration() {
        Instant expiration = getClaims().getExpiration();
        return expiration != null ? Date.from(expiration) : null;
    }
}
