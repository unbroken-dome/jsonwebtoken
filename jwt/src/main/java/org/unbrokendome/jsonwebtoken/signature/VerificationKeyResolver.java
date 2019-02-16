package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeader;

import javax.annotation.Nonnull;
import java.security.Key;


/**
 * Strategy interface for selecting a cryptographic key to use in verifying a JWT signature.
 *
 * @param <TVerificationKey> the type of verification key; must be a subclass of {@link Key}
 */
@FunctionalInterface
public interface VerificationKeyResolver<TVerificationKey extends Key> {

    /**
     * Selects a verification key for the given header and payload.
     *
     * @param header  the JOSE header of the token
     * @param payload the deserialized payload of the token
     * @return the verification key
     */
    @Nonnull
    TVerificationKey getVerificationKey(JoseHeader header, Object payload);
}
