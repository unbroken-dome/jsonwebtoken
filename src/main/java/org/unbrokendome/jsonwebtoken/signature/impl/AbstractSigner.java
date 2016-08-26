package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;


public abstract class AbstractSigner<T> extends SecurityAlgorithmSupport<T> implements Signer {

    private static final byte[] ENCODED_SEPARATOR = ".".getBytes(StandardCharsets.UTF_8);


    protected static byte[] getEncodedSeparator() {
        return ENCODED_SEPARATOR;
    }


    public AbstractSigner(AlgorithmProvider<T> provider) {
        super(provider);
    }


    @Override
    public final BinaryData sign(BinaryData header, BinaryData payload, Key key) throws JwsSignatureException {
        try {
            return calculateSignature(header, payload, key);
        } catch (SignatureException e) {
            throw new JwsSignatureException("Error calculating signature", e);
        }
    }


    protected abstract BinaryData calculateSignature(BinaryData header, BinaryData payload, Key key)
            throws SignatureException;
}
