package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.Jws;

import javax.annotation.Nonnull;


public interface JwsDecoder {

    @Nonnull
    Jws decode(String encoded) throws JwtMalformedTokenException;
}
