package org.unbrokendome.jsonwebtoken.signature.impl;

import com.google.common.io.ByteSource;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


public class PublicKeyFromPrivateKeyLoader implements KeyLoader<PublicKey> {

    private final KeyLoader<PrivateKey> privateKeyLoader;
    private final PublicKeyExtractor publicKeyExtractor;
    private final KeyFactory keyFactory;


    public PublicKeyFromPrivateKeyLoader(KeyLoader<PrivateKey> privateKeyLoader,
                                         PublicKeyExtractor publicKeyExtractor,
                                         KeyFactory keyFactory) {
        this.privateKeyLoader = privateKeyLoader;
        this.publicKeyExtractor = publicKeyExtractor;
        this.keyFactory = keyFactory;
    }


    @Override
    public PublicKey load(ByteSource source) throws IOException, InvalidKeySpecException {
        PrivateKey privateKey = privateKeyLoader.load(source);
        return publicKeyExtractor.publicKeyFromPrivateKey(privateKey, keyFactory);
    }
}
