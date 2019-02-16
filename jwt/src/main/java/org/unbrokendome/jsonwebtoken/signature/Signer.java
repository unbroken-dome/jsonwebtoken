package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;
import java.security.Key;


/**
 * Creates cryptographic signatures for tokens.
 *
 * @param <TSigningKey> the type of signing key
 */
public interface Signer<TSigningKey extends Key> {

    /**
     * Creates a cryptographic signature for a token.
     *
     * @param header  the serialized header, as a {@link BinaryData} instance
     * @param payload the serialized payload, as a {@link BinaryData} instance
     * @param key     the signing key that was selected for this token
     * @return the signature for the token, as a {@link BinaryData} instance
     * @throws JwsSignatureException if the signature cannot be created
     */
    @Nonnull
    BinaryData sign(BinaryData header, BinaryData payload, TSigningKey key) throws JwsSignatureException;
}
