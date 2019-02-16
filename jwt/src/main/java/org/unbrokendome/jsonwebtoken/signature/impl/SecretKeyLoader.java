package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;


/**
 * Implementation of {@link KeyLoader} that loads key material into a {@link SecretKeySpec}.
 */
final class SecretKeyLoader implements KeyLoader<SecretKey> {

    private final String algorithm;


    SecretKeyLoader(String algorithm) {
        this.algorithm = algorithm;
    }


    @Nonnull
    @Override
    public SecretKey load(IOSupplier<byte[]> source) throws IOException {
        byte[] bytes = source.get();
        return new SecretKeySpec(bytes, algorithm);
    }
}
