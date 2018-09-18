package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

import java.security.Key;


/**
 * A dummy implementation of {@link SigningKeyResolver} and {@link VerificationKeyResolver} that can be used
 * for the "NONE" algorithm if a resolver is required.
 */
public final class NoneKeyResolver implements SigningKeyResolver<Key>, VerificationKeyResolver<Key> {

    private static final NoneKeyResolver INSTANCE = new NoneKeyResolver();


    private NoneKeyResolver() {
    }


    /**
     * Gets the singleton instance of {@link NoneKeyResolver}.
     * @return the singleton instance
     */
    public static NoneKeyResolver getInstance() {
        return INSTANCE;
    }


    @Override
    public Key getSigningKey(JoseHeaderBuilder header, Object payload) {
        return NoneKey.getInstance();
    }


    @Override
    public Key getVerificationKey(JoseHeader header, Object payload) {
        return NoneKey.getInstance();
    }
}
