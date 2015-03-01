package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;

public class MacAlgorithmProvider implements AlgorithmProvider<Mac> {

	private final String algorithm;


	public MacAlgorithmProvider(String algorithm) {
		this.algorithm = algorithm;
	}


	@Override
	public Mac getInstance() throws NoSuchAlgorithmException {
		return Mac.getInstance(algorithm);
	}


	@Override
	public void returnInstance(Mac instance) {
	}
}
