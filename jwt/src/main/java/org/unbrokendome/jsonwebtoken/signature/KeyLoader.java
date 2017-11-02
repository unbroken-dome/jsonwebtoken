package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.IOSupplier;

import java.io.IOException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;


/**
 * Defines a strategy to load a key from a binary resource.
 *
 * @param <TKey> the type of key to be loaded
 */
public interface KeyLoader<TKey extends Key> {

    /**
     * Loads a key from a binary resource.
     *
     * @param source the binary resource from which the key should be loaded
     * @return the loaded key, never {@code null}
     * @throws IOException for I/O errors
     * @throws InvalidKeySpecException if the binary resource does not match the expected key format
     */
    TKey load(IOSupplier<byte[]> source) throws IOException, InvalidKeySpecException;
}
