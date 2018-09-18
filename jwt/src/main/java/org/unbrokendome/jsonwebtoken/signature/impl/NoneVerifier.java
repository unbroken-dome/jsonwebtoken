package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import javax.annotation.Nullable;
import java.security.Key;


public final class NoneVerifier implements Verifier<NoneKey> {

    private static final NoneVerifier INSTANCE = new NoneVerifier();


    private NoneVerifier() {
    }


    public static NoneVerifier getInstance() {
        return INSTANCE;
    }


    @Override
    public void verify(BinaryData header, BinaryData payload, BinaryData signature, NoneKey key) {
    }
}
