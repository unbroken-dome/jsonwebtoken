package org.unbrokendome.jsonwebtoken.signature.impl;

import org.apache.commons.lang3.tuple.Pair;
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
    public Pair<Signer<Key>, Verifier<Key>> createSignerAndVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return Pair.of(NoneSigner.getInstance(), NoneVerifier.getInstance());
    }


    @Override
    public Verifier<Key> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return NoneVerifier.getInstance();
    }
}
