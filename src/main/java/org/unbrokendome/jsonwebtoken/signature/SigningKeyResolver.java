package org.unbrokendome.jsonwebtoken.signature;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

public interface SigningKeyResolver {

	Key getSigningKey(JoseHeaderBuilder header, Object payload);
}
