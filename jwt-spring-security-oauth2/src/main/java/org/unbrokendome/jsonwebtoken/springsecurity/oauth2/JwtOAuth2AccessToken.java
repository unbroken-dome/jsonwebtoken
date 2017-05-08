package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.unbrokendome.jsonwebtoken.Claims;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;


public final class JwtOAuth2AccessToken implements OAuth2AccessToken {

    @Nonnull
    private final String tokenValue;
    @Nonnull
    private final Claims claims;
    @Nullable
    private final OAuth2RefreshToken refreshToken;
    @Nonnull
    private final Clock clock;


    public JwtOAuth2AccessToken(String tokenValue, Claims claims, @Nullable OAuth2RefreshToken refreshToken,
                                Clock clock) {
        this.tokenValue = tokenValue;
        this.claims = claims;
        this.refreshToken = refreshToken;
        this.clock = clock;
    }


    @Nonnull
    public Claims getClaims() {
        return claims;
    }


    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }


    @Override
    @Nullable
    public Set<String> getScope() {
        return JwtExtraClaims.getScope(claims);
    }


    @Override
    @Nullable
    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }


    @Override
    public String getTokenType() {
        return OAuth2AccessToken.BEARER_TYPE.toLowerCase();
    }


    @Override
    public boolean isExpired() {
        Instant expiration = claims.getExpiration();
        return expiration != null && expiration.isBefore(clock.instant());
    }


    @Override
    @Nullable
    public Date getExpiration() {
        Instant expiration = claims.getExpiration();
        return expiration != null ? Date.from(expiration) : null;
    }


    @Override
    public int getExpiresIn() {
        Instant expiration = claims.getExpiration();
        if (expiration != null) {
            return (int) Duration.between(clock.instant(), expiration).getSeconds();
        } else {
            return 0;
        }
    }


    @Override
    public String getValue() {
        return tokenValue;
    }
}
