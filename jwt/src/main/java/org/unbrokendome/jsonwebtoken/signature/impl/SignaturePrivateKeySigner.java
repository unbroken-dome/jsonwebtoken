package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import javax.annotation.Nonnull;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;


/**
 * Implementation of {@link org.unbrokendome.jsonwebtoken.signature.Signer Signer} that uses a {@link Signature}
 * algorithm and a private key.
 */
final class SignaturePrivateKeySigner extends AbstractSigner<Signature, PrivateKey> {

    SignaturePrivateKeySigner(AlgorithmProvider<Signature> provider) {
        super(provider);
    }


    @Override
    @Nonnull
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
