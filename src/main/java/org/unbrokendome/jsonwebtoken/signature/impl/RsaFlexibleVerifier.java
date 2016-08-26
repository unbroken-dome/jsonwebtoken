package org.unbrokendome.jsonwebtoken.signature.impl;

import com.google.common.base.Preconditions;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


public final class RsaFlexibleVerifier implements Verifier {

    private final Verifier privateKeyVerifier;
    private final Verifier publicKeyVerifier;


    public RsaFlexibleVerifier(AlgorithmProvider<Signature> provider, Signer signer) {
        this.privateKeyVerifier = new SignerVerifier(signer);
        this.publicKeyVerifier = new RsaPublicKeyVerifier(provider);
    }


    @Override
    public void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key)
            throws JwsSignatureException {
        Preconditions.checkArgument(key instanceof PrivateKey || key instanceof PublicKey,
                "Key must be either a PrivateKey or a PublicKey");

        if (key instanceof PrivateKey) {
            privateKeyVerifier.verify(header, payload, signature, key);
        } else {
            publicKeyVerifier.verify(header, payload, signature, key);
        }
    }
}
