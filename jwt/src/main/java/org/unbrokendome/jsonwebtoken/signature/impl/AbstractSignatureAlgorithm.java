package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.Key;


abstract class AbstractSignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key>
        implements SignatureAlgorithm<TSigningKey, TVerificationKey> {

    private final String jwaName;
    private final String jcaName;
    private final String jcaProvider;


    AbstractSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        this.jwaName = jwaName;
        this.jcaName = jcaName;
        this.jcaProvider = jcaProvider;
    }


    @Nonnull
    @Override
    public final String getJwaName() {
        return jwaName;
    }


    final String getJcaName() {
        return jcaName;
    }


    @Nullable
    final String getJcaProvider() {
        return jcaProvider;
    }
}
