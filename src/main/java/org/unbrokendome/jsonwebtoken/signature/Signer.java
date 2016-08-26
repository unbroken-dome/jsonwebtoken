package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.security.Key;


public interface Signer<TSigningKey extends Key> {

    BinaryData sign(BinaryData header, BinaryData payload, TSigningKey key) throws JwsSignatureException;
}
