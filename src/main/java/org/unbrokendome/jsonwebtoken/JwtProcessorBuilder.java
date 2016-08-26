package org.unbrokendome.jsonwebtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

import java.security.Key;


public interface JwtProcessorBuilder {

    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm algorithm, SigningKeyResolver signingKeyResolver,
                                          VerificationKeyResolver verificationKeyResolver);


    default JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm algorithm, Key key) {
        return signAndVerifyWith(algorithm, (h, p) -> key, (h, p) -> key);
    }


    JwtProcessorBuilder verifyWith(SignatureAlgorithm algorithm, VerificationKeyResolver keyResolver);


    default JwtProcessorBuilder verifyWith(SignatureAlgorithm algorithm, Key key) {
        return verifyWith(algorithm, (h, p) -> key);
    }


    JwtProcessorBuilder serializePayloadWith(PayloadSerializer<?> payloadSerializer);


    JwtProcessorBuilder configurePool(int minSize, int maxIdle);


    JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper);


    JwtProcessor build();
}
