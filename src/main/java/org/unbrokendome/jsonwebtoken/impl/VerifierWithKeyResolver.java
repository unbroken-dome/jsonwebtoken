package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;


class VerifierWithKeyResolver {

    private final Verifier verifier;
    private final VerificationKeyResolver verificationKeyResolver;


    public VerifierWithKeyResolver(Verifier verifier, VerificationKeyResolver verificationKeyResolver) {
        this.verifier = verifier;
        this.verificationKeyResolver = verificationKeyResolver;
    }


    public Verifier getVerifier() {
        return verifier;
    }


    public VerificationKeyResolver getVerificationKeyResolver() {
        return verificationKeyResolver;
    }
}
