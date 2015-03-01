package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.Jws;

public interface JwsDecoder {

	Jws decode(String encoded) throws JwtMalformedTokenException;
}
