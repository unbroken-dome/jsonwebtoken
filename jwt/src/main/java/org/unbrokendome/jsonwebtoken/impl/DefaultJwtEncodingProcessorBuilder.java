package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;
import org.unbrokendome.jsonwebtoken.encoding.DefaultHeaderSerializer;
import org.unbrokendome.jsonwebtoken.encoding.JwsCompactEncoder;
import org.unbrokendome.jsonwebtoken.encoding.payload.ByteBufferPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.DefaultPayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.encoding.payload.StringPayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.impl.NoneKeyResolver;

import javax.annotation.Nullable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;


public final class DefaultJwtEncodingProcessorBuilder
        extends AbstractJwtProcessorBuilder<JwtEncodingProcessor, JwtEncodeOnlyProcessorBuilder>
        implements JwtEncodeOnlyProcessorBuilder {

    private final List<PayloadSerializer> payloadSerializers = new ArrayList<>();
    private SignatureAlgorithm<?, ?> signingAlgorithm = SignatureAlgorithms.NONE;
    @Nullable
    private SigningKeyResolver<?> signingKeyResolver;


    @Override
    public JwtEncodeOnlyProcessorBuilder serializePayloadWith(PayloadSerializer payloadSerializer) {
        payloadSerializers.add(payloadSerializer);
        return this;
    }


    @Override
    public <TSigningKey extends Key>
    JwtEncodeOnlyProcessorBuilder signWith(SignatureAlgorithm<TSigningKey, ?> algorithm,
                                           SigningKeyResolver<TSigningKey> signingKeyResolver) {
        this.signingAlgorithm = algorithm;
        this.signingKeyResolver = signingKeyResolver;
        return this;
    }


    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public JwtEncodingProcessor build() {

        ObjectMapper objectMapper = getObjectMapper();
        addDefaultPayloadSerializers(objectMapper);

        Signer<?> signer = signingAlgorithm.createSigner(getPoolConfigurer());

        return new DefaultJwtEncodingProcessor(
                payloadSerializers,
                signingAlgorithm, signer,
                signingKeyResolver != null ? signingKeyResolver : NoneKeyResolver.getInstance(),
                new DefaultHeaderSerializer(objectMapper),
                new JwsCompactEncoder());
    }


    private void addDefaultPayloadSerializers(ObjectMapper objectMapper) {
        payloadSerializers.add(StringPayloadSerializer.getInstance());
        payloadSerializers.add(ByteBufferPayloadSerializer.getInstance());
        payloadSerializers.add(new DefaultPayloadSerializer(objectMapper));
    }
}
