package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

import java.security.Key;


public interface SigningKeyResolver<TSigningKey extends Key> {

    TSigningKey getSigningKey(JoseHeaderBuilder header, Object payload);
}
