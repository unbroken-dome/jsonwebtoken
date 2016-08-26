package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeader;

import java.security.Key;


public interface VerificationKeyResolver<TKey extends Key> {

    TKey getVerificationKey(JoseHeader header, Object payload);
}
