package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

import java.security.Key;


public final class SignerVerifier<TKey extends Key> implements Verifier<TKey> {

    private final Signer<TKey> signer;


    public SignerVerifier(Signer<TKey> signer) {
        this.signer = signer;
    }


    @Override
    public void verify(BinaryData header, BinaryData payload, BinaryData signature, TKey key)
            throws JwsSignatureException {
        BinaryData expectedSignature = signer.sign(header, payload, key);
        if (!expectedSignature.equals(signature)) {
            throw new JwsSignatureMismatchException("Incorrect signature");
        }
    }
}
