package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import javax.annotation.Nullable;
import java.security.Key;


/**
 * Implementation of {@link Verifier} that re-creates the signature using the {@link Signer} and compares it
 * with the signature to be verified.
 * <p>
 * This works only for symmetric cryptographic schemes, i.e. where the same key is used for both signing and
 * verification, and the signature does not contain any random element.
 *
 * @param <TKey> the type of key for signing/verification
 */
final class SignerVerifier<TKey extends Key> implements Verifier<TKey> {

    private final Signer<TKey> signer;


    SignerVerifier(Signer<TKey> signer) {
        this.signer = signer;
    }


    @Override
    public void verify(BinaryData header, BinaryData payload, BinaryData signature, @Nullable TKey key)
            throws JwsSignatureException {

        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        BinaryData expectedSignature = signer.sign(header, payload, key);
        if (!expectedSignature.equals(signature)) {
            throw new JwsSignatureMismatchException("Incorrect signature");
        }
    }
}
