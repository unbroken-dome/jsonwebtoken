package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;

import javax.annotation.Nullable;
import java.security.Key;


abstract class AbstractSignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key>
        implements SignatureAlgorithm<TSigningKey, TVerificationKey> {

    private final String jwaName;
    private final String jcaName;
    private final String jcaProvider;


    protected AbstractSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        this.jwaName = jwaName;
        this.jcaName = jcaName;
        this.jcaProvider = jcaProvider;
    }


    @Override
    public final String getJwaName() {
        return jwaName;
    }


    public final String getJcaName() {
        return jcaName;
    }


    @Nullable
    public final String getJcaProvider() {
        return jcaProvider;
    }
}
