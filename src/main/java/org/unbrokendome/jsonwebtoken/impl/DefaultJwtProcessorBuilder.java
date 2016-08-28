package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;
import org.unbrokendome.jsonwebtoken.encoding.JwsCompactEncoding;
import org.unbrokendome.jsonwebtoken.encoding.payload.ByteBufferPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.DefaultPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.StringPayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.*;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


public class DefaultJwtProcessorBuilder implements JwtProcessorBuilder {

    private Supplier<ObjectMapper> objectMapperSupplier = ObjectMapper::new;
    private ImmutableList.Builder<PayloadSerializer<?>> payloadSerializers = ImmutableList.builder();
    private SignatureAlgorithm<?, ?> signingAlgorithm = SignatureAlgorithms.NONE;
    private SigningKeyResolver<?> signingKeyResolver;
    private VerificationKeyResolver<?> verificationKeyResolver;

    private PoolConfigurer poolConfigurer;
    private Map<String, Function<PoolConfigurer, VerifierWithKeyResolver<?>>> verifierBuilders = new HashMap<>();


    @Override
    public JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapperSupplier = () -> objectMapper;
        return this;
    }


    @Override
    public JwtProcessorBuilder configurePool(int minSize, int maxIdle) {
        this.poolConfigurer = settings -> settings.min(minSize).maxIdle(maxIdle);
        return this;
    }


    @Override
    public JwtProcessorBuilder serializePayloadWith(PayloadSerializer<?> payloadSerializer) {
        payloadSerializers.add(payloadSerializer);
        return this;
    }


    @Override
    public <TSigningKey extends Key>
    JwtProcessorBuilder signWith(SignatureAlgorithm<TSigningKey, ?> algorithm,
                                 SigningKeyResolver<TSigningKey> signingKeyResolver) {
        this.signingAlgorithm = algorithm;
        this.signingKeyResolver = signingKeyResolver;
        return this;
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
    public <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        verifierBuilders.put(algorithm.getJwaName(),
                poolConfigurer -> new VerifierWithKeyResolver<>(algorithm.createVerifier(poolConfigurer), verificationKeyResolver));
        return this;
    }


    @Override
    public JwtProcessor build() {
        ObjectMapper objectMapper = objectMapperSupplier.get();

        addDefaultPayloadSerializers(objectMapper);

        Map<String, VerifierWithKeyResolver<?>> verifiers = new HashMap<>();

        Pair<? extends Signer<?>, ? extends Verifier<?>> signerAndVerifier =
                signingAlgorithm.createSignerAndVerifier(poolConfigurer);
        Signer<?> signer = signerAndVerifier.getLeft();

        //noinspection unchecked, rawtypes
        verifiers.put(signingAlgorithm.getJwaName(),
                new VerifierWithKeyResolver(signerAndVerifier.getRight(), verificationKeyResolver));

        if (verifierBuilders != null) {
            verifiers.putAll(Maps.transformValues(verifierBuilders,
                    f -> (f != null) ? f.apply(poolConfigurer) : null));
        }

        return new DefaultJwtProcessor(
                payloadSerializers.build(),
                signingAlgorithm,
                signer,
                signingKeyResolver,
                verifiers,
                new JwsCompactEncoding(objectMapper));
    }


    private void addDefaultPayloadSerializers(ObjectMapper objectMapper) {
        payloadSerializers.add(StringPayloadSerializer.getInstance());
        payloadSerializers.add(ByteBufferPayloadSerializer.getInstance());
        payloadSerializers.add(new DefaultPayloadSerializer(objectMapper));
    }
}
