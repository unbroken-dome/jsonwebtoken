package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.encoding.DefaultHeaderDeserializer;
import org.unbrokendome.jsonwebtoken.encoding.JwsCompactDecoder;
import org.unbrokendome.jsonwebtoken.encoding.payload.ByteBufferPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.DefaultPayloadDeserializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.StringPayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public final class DefaultJwtDecodingProcessorBuilder
        extends AbstractJwtProcessorBuilder<JwtDecodingProcessor, JwtDecodeOnlyProcessorBuilder>
        implements JwtDecodeOnlyProcessorBuilder {

    private final Map<String, Function<PoolConfigurer, VerifierWithKeyResolver<?>>> verifierBuilders = new HashMap<>();
    private final ImmutableList.Builder<PayloadDeserializer<?>> payloadDeserializers = ImmutableList.builder();


    @Override
    public JwtDecodeOnlyProcessorBuilder deserializePayloadWith(PayloadDeserializer<?> payloadDeserializer) {
        payloadDeserializers.add(payloadDeserializer);
        return this;
    }


    @Override
    public <TVerificationKey extends Key>
    JwtDecodeOnlyProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                             VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        verifierBuilders.put(algorithm.getJwaName(),
                poolConfigurer -> {
                    Verifier<TVerificationKey> verifier = algorithm.createVerifier(poolConfigurer);
                    return new VerifierWithKeyResolver<>(verifier, verificationKeyResolver);
                });
        return this;
    }


    @Override
    public JwtDecodingProcessor build() {

        ObjectMapper objectMapper = getObjectMapper();
        addDefaultPayloadDeserializers(objectMapper);

        PoolConfigurer poolConfigurer = getPoolConfigurer();

        @SuppressWarnings("ConstantConditions")
        Map<String, VerifierWithKeyResolver<?>> verifiers = Maps.transformValues(verifierBuilders,
                f -> f.apply(poolConfigurer));

        return new DefaultJwtDecodingProcessor(
                payloadDeserializers.build(),
                ImmutableMap.copyOf(verifiers),
                new DefaultHeaderDeserializer(objectMapper),
                new JwsCompactDecoder());
    }


    private void addDefaultPayloadDeserializers(ObjectMapper objectMapper) {
        payloadDeserializers.add(StringPayloadSerializer.getInstance());
        payloadDeserializers.add(ByteBufferPayloadSerializer.getInstance());
        payloadDeserializers.add(new DefaultPayloadDeserializer(objectMapper));
    }
}
