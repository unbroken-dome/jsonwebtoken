package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.security.Key;


public interface Verifier {

    void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key) throws JwsSignatureException;
}
