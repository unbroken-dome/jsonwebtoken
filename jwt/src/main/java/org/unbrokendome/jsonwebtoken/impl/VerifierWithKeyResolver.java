package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import javax.annotation.Nonnull;
import java.security.Key;


final class VerifierWithKeyResolver<TVerificationKey extends Key> {

    private final Verifier<TVerificationKey> verifier;
    private final VerificationKeyResolver<TVerificationKey> verificationKeyResolver;


    VerifierWithKeyResolver(Verifier<TVerificationKey> verifier,
                            VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        this.verifier = verifier;
        this.verificationKeyResolver = verificationKeyResolver;
    }


    @Nonnull
    public Verifier<TVerificationKey> getVerifier() {
        return verifier;
    }


    @Nonnull
    VerificationKeyResolver<TVerificationKey> getVerificationKeyResolver() {
        return verificationKeyResolver;
    }
}
