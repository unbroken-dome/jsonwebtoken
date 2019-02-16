package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import javax.annotation.Nonnull;
import javax.crypto.Mac;
import javax.crypto.SecretKey;


/**
 * Implementation of {@link org.unbrokendome.jsonwebtoken.signature.Signer Signer} that uses a {@link Mac}
 * algorithm and a secret key.
 */
final class MacSigner extends AbstractSigner<Mac, SecretKey> {

    MacSigner(AlgorithmProvider<Mac> macProvider) {
        super(macProvider);
    }


    @Override
    @Nonnull
    protected BinaryData calculateSignature(BinaryData header, BinaryData payload, SecretKey key) {
        byte[] signatureBytes = doWithAlgorithmSafely(mac -> {
            mac.init(key);
            mac.update(header.toByteBuffer());
            mac.update(getEncodedSeparator());
            mac.update(payload.toByteBuffer());
            return mac.doFinal();
        });

        return BinaryData.of(signatureBytes);
    }
}
