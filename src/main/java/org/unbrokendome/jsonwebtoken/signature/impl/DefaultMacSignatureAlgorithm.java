package org.unbrokendome.jsonwebtoken.signature.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProviders;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import java.util.Optional;


public class DefaultMacSignatureAlgorithm extends AbstractSignatureAlgorithm {

    public DefaultMacSignatureAlgorithm(String jwaName, String jcaName) {
        super(jwaName, jcaName);
    }


    @Override
    public Pair<Signer, Verifier> createSignerAndVerifier(Optional<PoolConfigurer> poolConfigurer) {
        Signer signer = new MacSigner(AlgorithmProviders.mac(getJcaName(), poolConfigurer));
        Verifier verifier = new SignerVerifier(signer);
        return Pair.of(signer, verifier);
    }


    @Override
    public Verifier createVerifier(Optional<PoolConfigurer> poolConfigurer) {
        return createSignerAndVerifier(poolConfigurer).getRight();
    }
}
