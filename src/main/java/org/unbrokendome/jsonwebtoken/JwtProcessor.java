package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.impl.DefaultClaims;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;

public interface JwtProcessor {

	String encode(Object payload) throws JwsSignatureException;


	<T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
			JwsUnsupportedAlgorithmException, JwsSignatureException;


	default Claims decodeClaims(String encodedToken) throws JwtMalformedTokenException,
			JwsUnsupportedAlgorithmException, JwsSignatureException {
		return decode(encodedToken, DefaultClaims.class);
	}
}
