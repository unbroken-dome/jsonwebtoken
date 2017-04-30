package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.*;
import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.signature.*;


public final class DefaultJwtProcessor implements JwtProcessor {

    private final JwtEncodingProcessor encodingProcessor;
    private final JwtDecodingProcessor decodingProcessor;


    public DefaultJwtProcessor(JwtEncodingProcessor encodingProcessor, JwtDecodingProcessor decodingProcessor) {
        this.encodingProcessor = encodingProcessor;
        this.decodingProcessor = decodingProcessor;
    }


    @Override
    public String encode(Object payload) throws JwsSignatureException {
        return encodingProcessor.encode(payload);
    }


    @Override
    public <T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
            JwsUnsupportedAlgorithmException, JwsSignatureException {
        return decodingProcessor.decode(encodedToken, payloadType);
    }
}
