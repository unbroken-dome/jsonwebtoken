package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.Jwt;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;
import org.unbrokendome.jsonwebtoken.encoding.HeaderSerializer;
import org.unbrokendome.jsonwebtoken.encoding.JwsEncoder;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;

import javax.annotation.Nonnull;
import java.security.Key;
import java.util.List;
import java.util.function.Consumer;


final class DefaultJwtEncodingProcessor<TSigningKey extends Key> implements JwtEncodingProcessor {

    private final List<Consumer<? super JoseHeaderBuilder>> headerProcessors;
    private final List<PayloadSerializer> payloadSerializers;
    private final SignatureAlgorithm<TSigningKey, ?> signingAlgorithm;
    private final Signer<TSigningKey> signer;
    private final SigningKeyResolver<TSigningKey> signingKeyResolver;
    private final HeaderSerializer headerSerializer;
    private final JwsEncoder jwsEncoder;


    DefaultJwtEncodingProcessor(List<Consumer<? super JoseHeaderBuilder>> headerProcessors,
                                List<PayloadSerializer> payloadSerializers,
                                SignatureAlgorithm<TSigningKey, ?> signingAlgorithm,
                                Signer<TSigningKey> signer,
                                SigningKeyResolver<TSigningKey> signingKeyResolver,
                                HeaderSerializer headerSerializer,
                                JwsEncoder jwsEncoder) {
        this.headerProcessors = headerProcessors;
        this.payloadSerializers = payloadSerializers;
        this.signingAlgorithm = signingAlgorithm;
        this.signer = signer;
        this.signingKeyResolver = signingKeyResolver;
        this.headerSerializer = headerSerializer;
        this.jwsEncoder = jwsEncoder;
    }


    @Nonnull
    @Override
    public String encode(Object payload) throws JwsSignatureException {
        JoseHeaderBuilder header = Jwt.header();

        for (Consumer<? super JoseHeaderBuilder> headerProcessor : headerProcessors) {
            headerProcessor.accept(header);
        }
        signingAlgorithm.createHeader(header);

        TSigningKey signingKey = signingKeyResolver.getSigningKey(header, payload);

        BinaryData headerBytes = headerSerializer.serialize(header);
        BinaryData payloadBytes = serializePayload(payload);

        BinaryData signature = signer.sign(headerBytes, payloadBytes, signingKey);

        return jwsEncoder.encode(headerBytes, payloadBytes, signature);
    }


    @Nonnull
    private BinaryData serializePayload(Object payload) {
        PayloadSerializer serializer = findPayloadSerializerForType(payload.getClass());
        return serializer.serialize(payload);
    }


    @Nonnull
    private <T> PayloadSerializer findPayloadSerializerForType(Class<T> payloadType) {
        return payloadSerializers
                .stream()
                .filter(ps -> ps.supports(payloadType))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("No payload serializer could handle the payload of type: "
                                + payloadType));
    }
}
