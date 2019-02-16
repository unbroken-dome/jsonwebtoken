package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


class PublicKeyFromPrivateKeyLoader implements KeyLoader<PublicKey> {

    private final KeyLoader<PrivateKey> privateKeyLoader;
    private final PublicKeyExtractor publicKeyExtractor;
    private final KeyFactory keyFactory;


    PublicKeyFromPrivateKeyLoader(KeyLoader<PrivateKey> privateKeyLoader,
                                  PublicKeyExtractor publicKeyExtractor,
                                  KeyFactory keyFactory) {
        this.privateKeyLoader = privateKeyLoader;
        this.publicKeyExtractor = publicKeyExtractor;
        this.keyFactory = keyFactory;
    }


    @Nonnull
    @Override
    public PublicKey load(IOSupplier<byte[]> source) throws IOException, InvalidKeySpecException {
        PrivateKey privateKey = privateKeyLoader.load(source);
        return publicKeyExtractor.publicKeyFromPrivateKey(privateKey, keyFactory);
    }
}
