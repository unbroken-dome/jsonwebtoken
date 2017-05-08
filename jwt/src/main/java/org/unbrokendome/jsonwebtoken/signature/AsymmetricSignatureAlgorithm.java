package org.unbrokendome.jsonwebtoken.signature;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;


public interface AsymmetricSignatureAlgorithm<TSigningKey extends PrivateKey, TVerificationKey extends PublicKey>
        extends SignatureAlgorithm<TSigningKey, TVerificationKey> {

    /**
     * Gets an instance of the key factory for this algorithm, which is used to construct
     * private and public keys from key material.
     *
     * @return the {@link KeyFactory} instance
     * @throws NoSuchAlgorithmException if the key algorithm is not supported by the installed security providers
     */
    KeyFactory getKeyFactory() throws NoSuchAlgorithmException;
}
