package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;


abstract class AbstractSigner<TAlgorithm, TSigningKey extends Key>
        extends SecurityAlgorithmSupport<TAlgorithm>
        implements Signer<TSigningKey> {

    private static final byte[] ENCODED_SEPARATOR = ".".getBytes(StandardCharsets.UTF_8);


    AbstractSigner(AlgorithmProvider<TAlgorithm> provider) {
        super(provider);
    }


    static byte[] getEncodedSeparator() {
        return ENCODED_SEPARATOR;
    }


    @Nonnull
    @Override
    public final BinaryData sign(BinaryData header, BinaryData payload, TSigningKey key) throws JwsSignatureException {
        try {
            return calculateSignature(header, payload, key);
        } catch (SignatureException e) {
            throw new JwsSignatureException("Error calculating signature", e);
        }
    }


    @Nonnull
    protected abstract BinaryData calculateSignature(BinaryData header, BinaryData payload, TSigningKey key)
            throws SignatureException;
}
