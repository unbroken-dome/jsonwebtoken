package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.*;

import java.security.Key;


public final class DefaultJwtProcessorBuilder implements JwtProcessorBuilder {

    private final JwtEncodeOnlyProcessorBuilder encodingProcessorBuilder =
            new DefaultJwtEncodingProcessorBuilder();
    private final JwtDecodeOnlyProcessorBuilder decodingProcessorBuilder =
            new DefaultJwtDecodingProcessorBuilder();


    @Override
    public JwtProcessorBuilder configurePool(int minSize, int maxIdle) {
        encodingProcessorBuilder.configurePool(minSize, maxIdle);
        decodingProcessorBuilder.configurePool(minSize, maxIdle);
        return this;
    }


    @Override
    public JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper) {
        encodingProcessorBuilder.setObjectMapper(objectMapper);
        decodingProcessorBuilder.setObjectMapper(objectMapper);
        return this;
    }


    @Override
    public <TSigningKey extends Key>
    JwtProcessorBuilder signWith(SignatureAlgorithm<TSigningKey, ?> algorithm,
                                 SigningKeyResolver<TSigningKey> signingKeyResolver) {
        encodingProcessorBuilder.signWith(algorithm, signingKeyResolver);
        return this;
    }


    @Override
    public JwtProcessorBuilder serializePayloadWith(PayloadSerializer payloadSerializer) {
        encodingProcessorBuilder.serializePayloadWith(payloadSerializer);
        return this;
    }


    @Override
    public <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        decodingProcessorBuilder.verifyWith(algorithm, verificationKeyResolver);
        return this;
    }


    @Override
    public JwtProcessorBuilder deserializePayloadWith(PayloadDeserializer<?> payloadDeserializer) {
        decodingProcessorBuilder.deserializePayloadWith(payloadDeserializer);
        return this;
    }


    @Override
    public JwtEncodeOnlyProcessorBuilder encodeOnly() {
        return encodingProcessorBuilder;
    }


    @Override
    public JwtDecodeOnlyProcessorBuilder decodeOnly() {
        return decodingProcessorBuilder;
    }


    @Override
    public <TSigningKey extends Key, TVerificationKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TSigningKey, TVerificationKey> algorithm,
                                          SigningKeyResolver<TSigningKey> signingKeyResolver,
                                          VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        signWith(algorithm, signingKeyResolver);
        verifyWith(algorithm, verificationKeyResolver);
        return this;
    }


    @Override
    public JwtProcessor build() {
        return new DefaultJwtProcessor(
                encodingProcessorBuilder.build(),
                decodingProcessorBuilder.build());
    }
}
