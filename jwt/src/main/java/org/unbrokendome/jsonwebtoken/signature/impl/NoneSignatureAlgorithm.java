package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nullable;
import java.security.Key;


public final class NoneSignatureAlgorithm implements SignatureAlgorithm<Key, Key> {

    private static final NoneSignatureAlgorithm INSTANCE = new NoneSignatureAlgorithm();


    private NoneSignatureAlgorithm() {
    }


    public static NoneSignatureAlgorithm getInstance() {
        return INSTANCE;
    }


    @Override
    public String getJwaName() {
        return "none";
    }


    @Override
    public Signer<Key> createSigner(@Nullable PoolConfigurer poolConfigurer) {
        return NoneSigner.getInstance();
    }


    @Override
    public Verifier<Key> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return NoneVerifier.getInstance();
    }
}
