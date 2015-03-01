package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;

public interface HeaderDeserializer {

	JoseHeader deserialize(BinaryData data) throws JwtMalformedTokenException;
}
