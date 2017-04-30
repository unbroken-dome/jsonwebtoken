package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.Jws;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.encoding.HeaderDeserializer;
import org.unbrokendome.jsonwebtoken.encoding.JwsDecoder;
import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import java.security.Key;
import java.util.List;
import java.util.Map;


public final class DefaultJwtDecodingProcessor implements JwtDecodingProcessor {

    private final List<PayloadDeserializer<?>> payloadDeserializers;
    private final Map<String, VerifierWithKeyResolver<?>> verifiers;
    private final HeaderDeserializer headerDeserializer;
    private final JwsDecoder jwsDecoder;


    public DefaultJwtDecodingProcessor(List<PayloadDeserializer<?>> payloadDeserializers,
                                       Map<String, VerifierWithKeyResolver<?>> verifiers,
                                       HeaderDeserializer headerDeserializer,
                                       JwsDecoder jwsDecoder) {
        this.payloadDeserializers = payloadDeserializers;
        this.verifiers = verifiers;
        this.headerDeserializer = headerDeserializer;
        this.jwsDecoder = jwsDecoder;
    }


    @Override
    public <T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
            JwsUnsupportedAlgorithmException, JwsSignatureException {
        Jws jws = jwsDecoder.decode(encodedToken);

        JoseHeader header = headerDeserializer.deserialize(jws.getHeader());
        T payload = deserializePayload(jws.getPayload(), payloadType);

        verifySignature(jws, header, payload);

        return payload;
    }


    private <T> T deserializePayload(BinaryData payloadBytes, Class<T> payloadType) throws JwtMalformedTokenException {
        PayloadDeserializer<T> payloadSerializer = findPayloadSerializerForType(payloadType);

        try {
            return payloadSerializer.deserialize(payloadBytes, payloadType);
        } catch (Exception ex) {
            throw new JwtMalformedTokenException("Error deserializing JWT payload", ex);
        }
    }


    private <T> PayloadDeserializer<T> findPayloadSerializerForType(Class<T> payloadType) {
        PayloadDeserializer<?> payloadDeserializer = payloadDeserializers
                .stream()
                .filter(ps -> ps.supports(payloadType))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("No payload serializer could handle the payload of type: "
                                + payloadType));
        // noinspection unchecked
        return (PayloadDeserializer<T>) payloadDeserializer;
    }


    private void verifySignature(Jws jws, JoseHeader header, Object payload)
            throws JwsUnsupportedAlgorithmException, JwsSignatureException {
        VerifierWithKeyResolver<?> verifierWithKeyResolver = getVerifierAndKeyResolver(header);

        VerificationKeyResolver<?> verificationKeyResolver = verifierWithKeyResolver.getVerificationKeyResolver();

        // noinspection rawtypes
        Verifier verifier = verifierWithKeyResolver.getVerifier();
        Key verificationKey = verificationKeyResolver.getVerificationKey(header, payload);

        //noinspection unchecked
        verifier.verify(jws.getHeader(), jws.getPayload(), jws.getSignature(), verificationKey);
    }


    private VerifierWithKeyResolver<?> getVerifierAndKeyResolver(JoseHeader header)
            throws JwsUnsupportedAlgorithmException {
        String algorithmName = header.getAlgorithm();
        VerifierWithKeyResolver<?> verifierWithKeyResolver = verifiers.get(algorithmName);
        if (verifierWithKeyResolver == null) {
            throw new JwsUnsupportedAlgorithmException(algorithmName);
        }
        return verifierWithKeyResolver;
    }
}
