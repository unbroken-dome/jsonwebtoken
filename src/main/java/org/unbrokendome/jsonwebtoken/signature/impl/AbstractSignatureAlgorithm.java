package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;

import java.security.Key;


public abstract class AbstractSignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key>
        implements SignatureAlgorithm<TSigningKey, TVerificationKey> {

    private final String jwaName;
    private final String jcaName;


    public AbstractSignatureAlgorithm(String jwaName, String jcaName) {
        this.jwaName = jwaName;
        this.jcaName = jcaName;
    }


    @Override
    public String getJwaName() {
        return jwaName;
    }


    public String getJcaName() {
        return jcaName;
    }
}
