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


public class DefaultRsaSignatureAlgorithm extends AbstractSignatureAlgorithm<PrivateKey, PublicKey> {

    public DefaultRsaSignatureAlgorithm(String jwaName, String jcaName) {
        super(jwaName, jcaName);
    }


    @Override
    public Pair<Signer<PrivateKey>, Verifier<PublicKey>>
    createSignerAndVerifier(@Nullable PoolConfigurer poolConfigurer) {
        AlgorithmProvider<Signature> provider = AlgorithmProviders.rsa(getJcaName(), poolConfigurer);
        Signer<PrivateKey> signer = new RsaPrivateKeySigner(provider);
        Verifier<PublicKey> verifier = new RsaPublicKeyVerifier(provider);

        return Pair.of(signer, verifier);
    }


    @Override
    public Verifier<PublicKey> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return new RsaPublicKeyVerifier(AlgorithmProviders.rsa(getJcaName(), poolConfigurer));
    }
}
