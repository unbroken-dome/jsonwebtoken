package org.unbrokendome.jsonwebtoken.signature.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProviders;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;


public final class MacSignatureAlgorithm extends AbstractSignatureAlgorithm<SecretKey, SecretKey> {

    public MacSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        super(jwaName, jcaName, jcaProvider);
    }


    public MacSignatureAlgorithm(String jwaName, String jcaName) {
        this(jwaName, jcaName, null);
    }


    @Override
    public Signer<SecretKey> createSigner(@Nullable PoolConfigurer poolConfigurer) {
        return new MacSigner(AlgorithmProviders.mac(getJcaName(), poolConfigurer));
    }


    @Override
    public Verifier<SecretKey> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return createSignerAndVerifier(poolConfigurer).getRight();
    }


    @Override
    public Pair<Signer<SecretKey>, Verifier<SecretKey>> createSignerAndVerifier(@Nullable PoolConfigurer poolConfigurer) {
        Signer<SecretKey> signer = createSigner(poolConfigurer);
        Verifier<SecretKey> verifier = new SignerVerifier<>(signer);
        return Pair.of(signer, verifier);
    }


    @Override
    public KeyLoader<SecretKey> getSigningKeyLoader() {
        return new SecretKeyLoader(getJcaName());
    }


    @Override
    public KeyLoader<SecretKey> getVerificationKeyLoader(boolean fromSigningKey) {
        return getSigningKeyLoader();
    }
}
