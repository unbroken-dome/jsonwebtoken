package org.unbrokendome.jsonwebtoken.signature;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import java.util.Optional;


public interface SignatureAlgorithm {

    String getJwaName();


    Pair<Signer, Verifier> createSignerAndVerifier(Optional<PoolConfigurer> poolConfigurer);


    Verifier createVerifier(Optional<PoolConfigurer> poolConfigurer);


    default void createHeader(JoseHeaderBuilder header) {
        header.setAlgorithm(getJwaName());
    }
}
