package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.security.Key;


/**
 * Verifies cryptographic signatures of JSON Web Tokens.
 *
 * @param <TVerificationKey> the type of verification key
 */
public interface Verifier<TVerificationKey extends Key> {

    /**
     * Attempts to verify the signature of a token.
     *
     * @param header    the serialized header, as a {@link BinaryData} instance
     * @param payload   the serialized payload, as a {@link BinaryData} instance
     * @param signature the signature blob, as a {@link BinaryData} instance
     * @param key       the verification key that was selected for this token
     * @throws JwsSignatureException if the signature could not be verified. Specifically, a
     *                               {@link JwsSignatureMismatchException} should be thrown if the signature is invalid.
     */
    void verify(BinaryData header, BinaryData payload, BinaryData signature, TVerificationKey key)
            throws JwsSignatureException;
}
