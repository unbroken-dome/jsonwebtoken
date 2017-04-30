package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import java.security.Key;


final class VerifierWithKeyResolver<TVerificationKey extends Key> {

    private final Verifier<TVerificationKey> verifier;
    private final VerificationKeyResolver<TVerificationKey> verificationKeyResolver;


    public VerifierWithKeyResolver(Verifier<TVerificationKey> verifier,
                                   VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        this.verifier = verifier;
        this.verificationKeyResolver = verificationKeyResolver;
    }


    public Verifier<TVerificationKey> getVerifier() {
        return verifier;
    }


    public VerificationKeyResolver<TVerificationKey> getVerificationKeyResolver() {
        return verificationKeyResolver;
    }
}
