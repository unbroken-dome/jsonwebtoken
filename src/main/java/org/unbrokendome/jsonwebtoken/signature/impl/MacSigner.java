package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import javax.crypto.Mac;
import javax.crypto.SecretKey;


public class MacSigner extends AbstractSigner<Mac, SecretKey> {

    public MacSigner(AlgorithmProvider<Mac> macProvider) {
        super(macProvider);
    }


    @Override
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
