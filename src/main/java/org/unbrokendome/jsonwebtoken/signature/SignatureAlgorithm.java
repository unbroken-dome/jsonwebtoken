package org.unbrokendome.jsonwebtoken.signature;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nullable;
import java.security.Key;
import java.util.Optional;


public interface SignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key> {

    String getJwaName();


    Pair<Signer<TSigningKey>, Verifier<TVerificationKey>> createSignerAndVerifier(
            @Nullable PoolConfigurer poolConfigurer);


    Verifier<TVerificationKey> createVerifier(@Nullable PoolConfigurer poolConfigurer);


    default void createHeader(JoseHeaderBuilder header) {
        header.setAlgorithm(getJwaName());
    }
}
