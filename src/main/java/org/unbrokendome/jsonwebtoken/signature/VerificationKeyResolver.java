package org.unbrokendome.jsonwebtoken.signature;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.JoseHeader;

public interface VerificationKeyResolver {

	Key getVerificationKey(JoseHeader header, Object payload);
}
