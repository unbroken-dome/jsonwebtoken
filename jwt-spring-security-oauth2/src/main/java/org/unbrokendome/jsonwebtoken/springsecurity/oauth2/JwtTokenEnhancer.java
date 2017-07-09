package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.unbrokendome.jsonwebtoken.Claims;
import org.unbrokendome.jsonwebtoken.ClaimsBuilder;
import org.unbrokendome.jsonwebtoken.Jwt;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;


public final class JwtTokenEnhancer implements TokenEnhancer {

    private final JwtEncodingProcessor jwtProcessor;
    private final Clock clock;


    public JwtTokenEnhancer(JwtEncodingProcessor jwtProcessor, Clock clock) {
        this.jwtProcessor = jwtProcessor;
        this.clock = clock;
    }


    public JwtTokenEnhancer(JwtEncodingProcessor jwtProcessor) {
        this(jwtProcessor, Clock.systemDefaultZone());
    }


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Claims claims = createAccessTokenClaims(accessToken, authentication);
        String encodedAccessToken = jwtProcessor.encodeUnchecked(claims);
        OAuth2RefreshToken newRefreshToken = createRefreshToken(authentication, accessToken.getRefreshToken());

        return new JwtOAuth2AccessToken(encodedAccessToken, claims, newRefreshToken, clock);

    }


    private String getTokenId(OAuth2AccessToken accessToken) {
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        return String.valueOf(additionalInformation.getOrDefault(Claims.ID, accessToken.getValue()));
    }


    @Nonnull
    private Claims createAccessTokenClaims(OAuth2AccessToken accessToken,
                                           OAuth2Authentication authentication) {
        String tokenId = getTokenId(accessToken);

        ClaimsBuilder claims = Jwt.claims()
                .setId(tokenId)
                .setSubject(authentication.getName())
                .setIssuedAt(clock.instant())
                .setExpiration(accessToken.getExpiration().toInstant());
        JwtExtraClaims.setScope(claims, accessToken.getScope());
        JwtExtraClaims.setClientId(claims, authentication.getOAuth2Request().getClientId());
        JwtExtraClaims.setAuthorties(claims,
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
        return claims.build();
    }


    @Nullable
    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication,
                                                  @Nullable OAuth2RefreshToken refreshToken) {
        OAuth2RefreshToken newRefreshToken = null;
        if (refreshToken != null) {

            Instant refreshTokenExpiration = getRefreshTokenExpiration(refreshToken);

            Claims refreshTokenClaims = createRefreshTokenClaims(authentication, refreshToken, refreshTokenExpiration);

            String encodedRefreshToken = jwtProcessor.encodeUnchecked(refreshTokenClaims);

            if (refreshTokenExpiration != null) {
                newRefreshToken = new ExpiringJwtOAuth2RefreshToken(encodedRefreshToken, refreshTokenClaims);
            } else {
                newRefreshToken = new NonExpiringJwtOAuth2RefreshToken(encodedRefreshToken, refreshTokenClaims);
            }
        }
        return newRefreshToken;
    }


    @Nullable
    private Instant getRefreshTokenExpiration(@Nullable OAuth2RefreshToken refreshToken) {
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
            if (expiringRefreshToken.getExpiration() != null) {
                return expiringRefreshToken.getExpiration().toInstant();
            }
        }
        return null;
    }


    @Nonnull
    private Claims createRefreshTokenClaims(OAuth2Authentication authentication,
                                            OAuth2RefreshToken refreshToken,
                                            @Nullable Instant refreshTokenExpiration) {
        ClaimsBuilder refreshTokenClaims = Jwt.claims()
                .setId(refreshToken.getValue())
                .setAudience("refresh")
                .setSubject(authentication.getName())
                .setIssuedAt(clock.instant());
        JwtExtraClaims.setClientId(refreshTokenClaims, authentication.getOAuth2Request().getClientId());
        JwtExtraClaims.setScope(refreshTokenClaims, authentication.getOAuth2Request().getScope());
        if (refreshTokenExpiration != null) {
            refreshTokenClaims.setExpiration(refreshTokenExpiration);
        }
        return refreshTokenClaims.build();
    }
}
