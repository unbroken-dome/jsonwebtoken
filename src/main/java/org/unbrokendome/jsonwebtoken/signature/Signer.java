package org.unbrokendome.jsonwebtoken.signature;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.BinaryData;

public interface Signer {

	BinaryData sign(BinaryData header, BinaryData payload, Key key) throws JwsSignatureException;
}
