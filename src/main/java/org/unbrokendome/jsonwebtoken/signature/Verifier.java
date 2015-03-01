package org.unbrokendome.jsonwebtoken.signature;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.BinaryData;

public interface Verifier {

	void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key) throws JwsSignatureException;
}
