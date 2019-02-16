package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;

import javax.annotation.Nonnull;


public interface HeaderDeserializer {

    @Nonnull
    JoseHeader deserialize(BinaryData data) throws JwtMalformedTokenException;
}
