package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;


public final class RsaPrivateKeySigner extends AbstractSigner<Signature, PrivateKey> {

    public RsaPrivateKeySigner(AlgorithmProvider<Signature> provider) {
        super(provider);
    }


    @Override
    protected final BinaryData calculateSignature(BinaryData header, BinaryData payload, PrivateKey key)
            throws SignatureException {

        byte[] sigBytes = doWithAlgorithm(sig -> {
            sig.initSign(key);
            sig.update(header.toByteBuffer());
            sig.update(getEncodedSeparator());
            sig.update(payload.toByteBuffer());
            return sig.sign();
        });

        return BinaryData.of(sigBytes);
    }
}
