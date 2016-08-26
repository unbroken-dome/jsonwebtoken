package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.security.Key;


public interface Signer {

    BinaryData sign(BinaryData header, BinaryData payload, Key key) throws JwsSignatureException;
}
