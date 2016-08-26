package org.unbrokendome.jsonwebtoken.signature.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProviders;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nullable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


public class DefaultAsymmetricSignatureAlgorithm extends AbstractSignatureAlgorithm<PrivateKey, PublicKey> {

    public DefaultAsymmetricSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        super(jwaName, jcaName, jcaProvider);
    }


    public DefaultAsymmetricSignatureAlgorithm(String jwaName, String jcaName) {
        this(jwaName, jcaName, null);
    }


    @Override
    public Pair<Signer<PrivateKey>, Verifier<PublicKey>>
    createSignerAndVerifier(@Nullable PoolConfigurer poolConfigurer) {

        AlgorithmProvider<Signature> provider =
                AlgorithmProviders.signature(getJcaName(), getJcaProvider(), poolConfigurer);

        Signer<PrivateKey> signer = new SignaturePrivateKeySigner(provider);
        Verifier<PublicKey> verifier = new SignaturePublicKeyVerifier(provider);

        return Pair.of(signer, verifier);
    }


    @Override
    public Verifier<PublicKey> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return new SignaturePublicKeyVerifier(AlgorithmProviders.signature(getJcaName(), poolConfigurer));
    }
}
