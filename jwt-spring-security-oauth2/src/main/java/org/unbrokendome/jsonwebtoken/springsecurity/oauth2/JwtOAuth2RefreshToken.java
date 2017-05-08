package org.unbrokendome.jsonwebtoken.springsecurity.oauth2;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.unbrokendome.jsonwebtoken.Claims;

import javax.annotation.Nonnull;


public interface JwtOAuth2RefreshToken extends OAuth2RefreshToken {

    @Nonnull
    Claims getClaims();
}
