package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

import java.security.Key;


/**
 * Strategy interface for selecting a cryptographic key for signing a JWT.
 *
 * @param <TSigningKey> the type of signing key; must be a subclass of {@link Key}
 */
@FunctionalInterface
public interface SigningKeyResolver<TSigningKey extends Key> {

    /**
     * Selects a signing key for the given payload.
     *
     * The JWT header under construction is passed in as a {@link JoseHeaderBuilder} builder instance, so
     * implementations of this method may modify the header. This may be useful, for example, to store an ID
     * of the selected key in the header using the {@link JoseHeaderBuilder#setKeyId}, so the correct key can later
     * be selected when verifying the signature.
     *
     * @param header the JOSE header under construction, as a {@link JoseHeaderBuilder} instance. The method may
     *               make modifications to the builder, which will be reflected in the final token.
     * @param payload the payload for which to create the signature
     * @return the selected signing key
     */
    TSigningKey getSigningKey(JoseHeaderBuilder header, Object payload);
}
