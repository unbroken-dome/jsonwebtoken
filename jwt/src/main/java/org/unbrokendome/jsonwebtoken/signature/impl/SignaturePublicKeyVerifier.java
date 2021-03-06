package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


/**
 * Implementation of {@link org.unbrokendome.jsonwebtoken.signature.Verifier Verifier} that uses a {@link Signature}
 * algorithm and a public key.
 */
final class SignaturePublicKeyVerifier
        extends SecurityAlgorithmSupport<Signature>
        implements Verifier<PublicKey> {

    private static final byte[] ENCODED_SEPARATOR = ".".getBytes(StandardCharsets.UTF_8);


    SignaturePublicKeyVerifier(AlgorithmProvider<Signature> provider) {
        super(provider);
    }


    @Override
    public void verify(BinaryData header, BinaryData payload, BinaryData signature, @Nullable PublicKey key)
            throws JwsSignatureException {

        if (key == null) {
            throw new IllegalArgumentException("Verification key must not be null");
        }

        try {
            if (!doVerify(header, payload, signature, key)) {
                throw new JwsSignatureMismatchException("Incorrect signature");
            }
        } catch (SignatureException e) {
            throw new JwsSignatureException("Bad signature", e);
        }
    }


    private boolean doVerify(BinaryData header, BinaryData payload, BinaryData signature, PublicKey publicKey)
            throws SignatureException {
        return doWithAlgorithm(sig -> {
            sig.initVerify(publicKey);

            sig.update(header.toByteBuffer());
            sig.update(ENCODED_SEPARATOR);
            sig.update(payload.toByteBuffer());

            return sig.verify(signature.toByteArray());
        });
    }
}
