package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;


/**
 * Implementation of {@link KeyLoader} that loads key material into a {@link SecretKeySpec}.
 */
public final class SecretKeyLoader implements KeyLoader<SecretKey> {

    private final String algorithm;


    public SecretKeyLoader(String algorithm) {
        this.algorithm = algorithm;
    }


    @Override
    public SecretKey load(IOSupplier<byte[]> source) throws IOException {
        byte[] bytes = source.get();
        return new SecretKeySpec(bytes, algorithm);
    }
}
