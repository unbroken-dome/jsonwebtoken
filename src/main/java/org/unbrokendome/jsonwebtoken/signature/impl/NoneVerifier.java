package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.Key;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.Verifier;

public final class NoneVerifier implements Verifier {

	private static final NoneVerifier INSTANCE = new NoneVerifier();


	private NoneVerifier() {
	}


	public static NoneVerifier getInstance() {
		return INSTANCE;
	}


	@Override
	public void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key) {
	}
}
