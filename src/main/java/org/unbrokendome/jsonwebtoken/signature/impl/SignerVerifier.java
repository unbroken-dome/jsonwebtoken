package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

public final class SignerVerifier implements Verifier {

	private final Signer signer;


	public SignerVerifier(Signer signer) {
		this.signer = signer;
	}


	@Override
	public void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key)
			throws JwsSignatureException {
		BinaryData expectedSignature = signer.sign(header, payload, key);
		if (!expectedSignature.equals(signature)) {
			throw new JwsSignatureMismatchException("Incorrect signature");
		}
	}
}
