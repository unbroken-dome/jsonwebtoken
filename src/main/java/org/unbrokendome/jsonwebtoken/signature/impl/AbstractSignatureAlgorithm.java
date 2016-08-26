package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;

import javax.annotation.Nullable;
import java.security.Key;


public abstract class AbstractSignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key>
        implements SignatureAlgorithm<TSigningKey, TVerificationKey> {

    private final String jwaName;
    private final String jcaName;
    private final String jcaProvider;


    public AbstractSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        this.jwaName = jwaName;
        this.jcaName = jcaName;
        this.jcaProvider = jcaProvider;
    }


    @Override
    public String getJwaName() {
        return jwaName;
    }


    public String getJcaName() {
        return jcaName;
    }


    @Nullable
    public String getJcaProvider() {
        return jcaProvider;
    }
}
