package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.GeneralSecurityException;
import java.security.Signature;

public class RsaPoolableObject extends AbstractAlgorithmPoolableObject<Signature> {

	public RsaPoolableObject(String algorithm) {
		super(algorithm);
	}


	@Override
	protected Signature getInstance(String algorithm) throws GeneralSecurityException {
		return Signature.getInstance(algorithm);
	}
}
