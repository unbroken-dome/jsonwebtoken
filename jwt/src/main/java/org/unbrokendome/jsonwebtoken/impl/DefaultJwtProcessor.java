package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;

import javax.annotation.Nonnull;


final class DefaultJwtProcessor implements JwtProcessor {

    private final JwtEncodingProcessor encodingProcessor;
    private final JwtDecodingProcessor decodingProcessor;


    DefaultJwtProcessor(JwtEncodingProcessor encodingProcessor, JwtDecodingProcessor decodingProcessor) {
        this.encodingProcessor = encodingProcessor;
        this.decodingProcessor = decodingProcessor;
    }


    @Nonnull
    @Override
    public String encode(Object payload) throws JwsSignatureException {
        return encodingProcessor.encode(payload);
    }


    @Nonnull
    @Override
    public <T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
            JwsUnsupportedAlgorithmException, JwsSignatureException {
        return decodingProcessor.decode(encodedToken, payloadType);
    }
}
