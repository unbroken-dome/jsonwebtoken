package org.unbrokendome.jsonwebtoken.signature.impl;

import javax.annotation.Nonnull;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


/**
 * Strategy to derive a public key from a private key.
 */
public interface PublicKeyExtractor {

    boolean supports(PrivateKey privateKey);

    @Nonnull
    PublicKey publicKeyFromPrivateKey(PrivateKey privateKey, KeyFactory keyFactory) throws InvalidKeySpecException;
}
