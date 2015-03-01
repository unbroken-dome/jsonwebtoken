package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;

public abstract class AbstractSignatureAlgorithm implements SignatureAlgorithm {

	private final String jwaName;
	private final String jcaName;


	public AbstractSignatureAlgorithm(String jwaName, String jcaName) {
		this.jwaName = jwaName;
		this.jcaName = jcaName;
	}


	@Override
	public String getJwaName() {
		return jwaName;
	}


	public String getJcaName() {
		return jcaName;
	}
}
