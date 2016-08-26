package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.security.Key;


public interface Verifier<TVerificationKey extends Key> {

    void verify(BinaryData header, BinaryData payload, BinaryData signature, TVerificationKey key)
            throws JwsSignatureException;
}
