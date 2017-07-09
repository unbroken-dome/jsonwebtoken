package org.unbrokendome.jsonwebtoken.signature.impl;

import com.google.common.io.ByteSource;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.Supplier;


/**
 * Implementation of {@link KeyLoader} that loads public keys from X.509-encoded key material.
 */
public class X509PublicKeyLoader implements KeyLoader<PublicKey> {

    private final Supplier<KeyFactory> keyFactorySupplier;


    public X509PublicKeyLoader(Supplier<KeyFactory> keyFactorySupplier) {
        this.keyFactorySupplier = keyFactorySupplier;
    }


    @Override
    public PublicKey load(ByteSource source) throws IOException, InvalidKeySpecException {

        KeySpec keySpec = new X509EncodedKeySpec(source.read());

        KeyFactory keyFactory = keyFactorySupplier.get();
        return keyFactory.generatePublic(keySpec);
    }
}