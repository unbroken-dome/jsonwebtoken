package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nullable;
import java.io.InputStream;
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


    @Nullable
    @Override
    public KeyLoader<Key> getSigningKeyLoader() {
        return NoneKeyLoader.INSTANCE;
    }


    @Override
    public KeyLoader<Key> getVerificationKeyLoader(boolean fromSigningKey) {
        return NoneKeyLoader.INSTANCE;
    }


    private static class NoneKeyLoader implements KeyLoader<Key> {

        public static NoneKeyLoader INSTANCE = new NoneKeyLoader();

        private NoneKeyLoader() {
        }

        @Override
        public Key load(IOSupplier<byte[]> source) {
            return NoneKey.getInstance();
        }
    }
}
