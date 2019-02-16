package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.annotation.Nonnull;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public final class DefaultJwtDecodingProcessorBuilder
        extends AbstractJwtProcessorBuilder<JwtDecodingProcessor, JwtDecodeOnlyProcessorBuilder>
        implements JwtDecodeOnlyProcessorBuilder {

    private final Map<String, Function<PoolConfigurer, VerifierWithKeyResolver<?>>> verifierBuilders = new HashMap<>();
    private final List<PayloadDeserializer<?>> payloadDeserializers = new ArrayList<>();


    @Nonnull
    @Override
    public JwtDecodeOnlyProcessorBuilder deserializePayloadWith(PayloadDeserializer<?> payloadDeserializer) {
        payloadDeserializers.add(payloadDeserializer);
        return this;
    }


    @Nonnull
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


    @Nonnull
    @Override
    public JwtDecodingProcessor build() {

        ObjectMapper objectMapper = getObjectMapper();
        addDefaultPayloadDeserializers(objectMapper);

        PoolConfigurer poolConfigurer = getPoolConfigurer();

        Map<String, VerifierWithKeyResolver<?>> verifiers = new HashMap<>(verifierBuilders.size());
        verifierBuilders.forEach((key, f) -> verifiers.put(key, f.apply(poolConfigurer)));

        return new DefaultJwtDecodingProcessor(
                payloadDeserializers,
                verifiers,
                new DefaultHeaderDeserializer(objectMapper),
                new JwsCompactDecoder());
    }


    private void addDefaultPayloadDeserializers(ObjectMapper objectMapper) {
        payloadDeserializers.add(StringPayloadSerializer.getInstance());
        payloadDeserializers.add(ByteBufferPayloadSerializer.getInstance());
        payloadDeserializers.add(new DefaultPayloadDeserializer(objectMapper));
    }
}
