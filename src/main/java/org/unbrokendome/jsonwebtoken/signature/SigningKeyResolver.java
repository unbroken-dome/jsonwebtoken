package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

import java.security.Key;


public interface SigningKeyResolver {

    Key getSigningKey(JoseHeaderBuilder header, Object payload);
}
