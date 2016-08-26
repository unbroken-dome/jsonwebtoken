package org.unbrokendome.jsonwebtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public interface JwtProcessorBuilder {

    <TSigningKey extends Key, TVerificationKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TSigningKey, TVerificationKey> algorithm,
                                          SigningKeyResolver<TSigningKey> signingKeyResolver,
                                          VerificationKeyResolver<TVerificationKey> verificationKeyResolver);


    default <TKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TKey, TKey> algorithm, TKey key) {
        return signAndVerifyWith(algorithm, (h, p) -> key, (h, p) -> key);
    }


    default JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<PrivateKey, PublicKey> algorithm,
                                                  KeyPair keyPair) {
        return signAndVerifyWith(algorithm,
                (h, p) -> keyPair.getPrivate(),
                (h, p) -> keyPair.getPublic());
    }


    <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   VerificationKeyResolver<TVerificationKey> keyResolver);


    default <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   TVerificationKey key) {
        return verifyWith(algorithm, (VerificationKeyResolver<TVerificationKey>) ((h, p) -> key));
    }


    JwtProcessorBuilder serializePayloadWith(PayloadSerializer<?> payloadSerializer);


    JwtProcessorBuilder configurePool(int minSize, int maxIdle);


    JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper);


    JwtProcessor build();
}
