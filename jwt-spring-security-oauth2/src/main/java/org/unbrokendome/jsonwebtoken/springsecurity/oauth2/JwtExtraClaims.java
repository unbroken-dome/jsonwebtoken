package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.unbrokendome.jsonwebtoken.Claims;
import org.unbrokendome.jsonwebtoken.ClaimsBuilder;

import javax.annotation.Nullable;
import java.util.Set;


public final class JwtExtraClaims {

    private static final String CLIENT_ID = AccessTokenConverter.CLIENT_ID;
    private static final String SCOPE = AccessTokenConverter.SCOPE;
    private static final String AUTHORITIES = AccessTokenConverter.AUTHORITIES;

    private JwtExtraClaims() {
    }


    @Nullable
    public static String getClientId(Claims claims) {
        return claims.getString(CLIENT_ID);
    }


    public static void setClientId(ClaimsBuilder claimsBuilder, String clientId) {
        claimsBuilder.set(CLIENT_ID, clientId);
    }


    @Nullable
    public static Set<String> getScope(Claims claims) {
        return claims.getStringSet(SCOPE);
    }


    public static void setScope(ClaimsBuilder claimsBuilder, Set<String> scope) {
        claimsBuilder.set(SCOPE, scope);
    }


    @Nullable
    public static Set<String> getAuthorities(Claims claims) {
        return claims.getStringSet(AUTHORITIES);
    }


    public static void setAuthorties(ClaimsBuilder claimsBuilder, Set<String> authorities) {
        claimsBuilder.set(AUTHORITIES, authorities);
    }
}
