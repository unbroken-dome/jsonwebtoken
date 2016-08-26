package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nullable;
import java.security.Key;


public interface Verifier<TVerificationKey extends Key> {

    void verify(BinaryData header, BinaryData payload, BinaryData signature, @Nullable TVerificationKey key)
            throws JwsSignatureException;
}
