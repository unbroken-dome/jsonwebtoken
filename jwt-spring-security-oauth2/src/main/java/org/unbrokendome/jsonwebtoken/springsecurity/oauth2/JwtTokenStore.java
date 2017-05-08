package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.unbrokendome.jsonwebtoken.Claims;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtException;

import javax.annotation.Nullable;
import java.time.Clock;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public final class JwtTokenStore extends AbstractRichTokenStore {

    private final JwtDecodingProcessor jwtProcessor;
    @Nullable
    private final ApprovalStore approvalStore;
    private final Clock clock;


    public JwtTokenStore(JwtDecodingProcessor jwtProcessor,
                         @Nullable ApprovalStore approvalStore,
                         Clock clock) {
        this.jwtProcessor = jwtProcessor;
        this.approvalStore = approvalStore;
        this.clock = clock;
    }


    public JwtTokenStore(JwtDecodingProcessor jwtProcessor,
                         @Nullable ApprovalStore approvalStore) {
        this(jwtProcessor, approvalStore, Clock.systemDefaultZone());
    }


    public JwtTokenStore(JwtDecodingProcessor jwtProcessor,
                         Clock clock) {
        this(jwtProcessor, null, clock);
    }


    public JwtTokenStore(JwtDecodingProcessor jwtProcessor) {
        this(jwtProcessor, (ApprovalStore) null);
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        if (token instanceof JwtOAuth2AccessToken) {
            return readAuthentication(((JwtOAuth2AccessToken) token).getClaims());
        } else {
            return readAuthentication(token.getValue());
        }
    }


    @Override
    public OAuth2Authentication readAuthentication(String token) {
        Claims claims = decodeClaims(token);
        return readAuthentication(claims);
    }


    private OAuth2Authentication readAuthentication(Claims claims) {

        Set<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);

        OAuth2Request request = new OAuth2Request(
                getRequestParametersFromClaims(claims),
                JwtExtraClaims.getClientId(claims),
                authorities,
                true,
                JwtExtraClaims.getScope(claims),
                claims.getAudiences(),
                null, null, null);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

        return new OAuth2Authentication(request, authentication);
    }


    private Map<String, String> getRequestParametersFromClaims(Claims claims) {
        Map<String, Object> claimsMap = claims.asMap();
        Map<String, String> requestParameters = new LinkedHashMap<>(claimsMap.size());
        for (Map.Entry<String, Object> entry : claimsMap.entrySet()) {
            if (entry.getValue() != null) {
                requestParameters.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return Collections.unmodifiableMap(requestParameters);
    }


    @Nullable
    private Set<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        Set<String> authorities = JwtExtraClaims.getAuthorities(claims);
        if (authorities != null) {
            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }


    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Claims claims = decodeClaims(tokenValue);
        return new JwtOAuth2AccessToken(tokenValue, claims, null, clock);
    }


    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        if (token instanceof JwtOAuth2RefreshToken) {
            Claims claims = ((JwtOAuth2RefreshToken) token).getClaims();
            return readAuthentication(claims);
        } else {
            return readAuthentication(token.getValue());
        }
    }


    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Claims claims = decodeClaims(tokenValue);

        Set<String> audiences = claims.getAudiences();
        if (audiences == null || !audiences.contains("refresh")) {
            throw new InvalidTokenException("Encoded token is not a refresh token");
        }

        if (claims.getExpiration() != null) {
            return new ExpiringJwtOAuth2RefreshToken(tokenValue, claims);
        } else {
            return new NonExpiringJwtOAuth2RefreshToken(tokenValue, claims);
        }
    }


    private Claims decodeClaims(String tokenValue) {
        try {
            return jwtProcessor.decodeClaims(tokenValue);
        } catch (JwtException ex) {
            throw new InvalidTokenException("Invalid token", ex);
        }
    }
}
