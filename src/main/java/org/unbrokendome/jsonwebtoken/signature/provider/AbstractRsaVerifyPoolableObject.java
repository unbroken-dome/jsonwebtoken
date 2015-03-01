package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.Signature;
import java.security.SignatureException;

import nf.fr.eraasoft.pool.PoolException;

public abstract class AbstractRsaVerifyPoolableObject extends AbstractAlgorithmPoolableObject<Signature> {

	public AbstractRsaVerifyPoolableObject(String algorithm) {
		super(algorithm);
	}


	@Override
	public void activate(Signature signature) throws PoolException {
	}


	@Override
	public void passivate(Signature signature) {
		try {
			signature.verify(null);
		}
		catch (SignatureException e) {
		}
	}
}