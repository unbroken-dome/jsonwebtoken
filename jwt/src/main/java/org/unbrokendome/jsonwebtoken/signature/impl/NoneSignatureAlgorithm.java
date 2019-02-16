package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class NoneSignatureAlgorithm implements SignatureAlgorithm<NoneKey, NoneKey> {

    private static final NoneSignatureAlgorithm INSTANCE = new NoneSignatureAlgorithm();


    private NoneSignatureAlgorithm() {
    }


    public static NoneSignatureAlgorithm getInstance() {
        return INSTANCE;
    }


    @Nonnull
    @Override
    public String getJwaName() {
        return "none";
    }


    @Nonnull
    @Override
    public Signer<NoneKey> createSigner(@Nullable PoolConfigurer poolConfigurer) {
        return NoneSigner.getInstance();
    }


    @Nonnull
    @Override
    public Verifier<NoneKey> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return NoneVerifier.getInstance();
    }


    @Nullable
    @Override
    public KeyLoader<NoneKey> getSigningKeyLoader() {
        return NoneKeyLoader.INSTANCE;
    }


    @Override
    public KeyLoader<NoneKey> getVerificationKeyLoader(boolean fromSigningKey) {
        return NoneKeyLoader.INSTANCE;
    }


    private static class NoneKeyLoader implements KeyLoader<NoneKey> {

        static final NoneKeyLoader INSTANCE = new NoneKeyLoader();


        private NoneKeyLoader() {
        }


        @Nonnull
        @Override
        public NoneKey load(IOSupplier<byte[]> source) {
            return NoneKey.getInstance();
        }
    }
}
