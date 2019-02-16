package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.function.Supplier;


final class PKCS8PrivateKeyLoader implements KeyLoader<PrivateKey> {

    private final Supplier<KeyFactory> keyFactorySupplier;


    PKCS8PrivateKeyLoader(Supplier<KeyFactory> keyFactorySupplier) {
        this.keyFactorySupplier = keyFactorySupplier;
    }


    @Nonnull
    @Override
    public PrivateKey load(IOSupplier<byte[]> source) throws IOException, InvalidKeySpecException {

        KeySpec keySpec = new PKCS8EncodedKeySpec(source.get());

        KeyFactory keyFactory = keyFactorySupplier.get();
        return keyFactory.generatePrivate(keySpec);
    }
}
