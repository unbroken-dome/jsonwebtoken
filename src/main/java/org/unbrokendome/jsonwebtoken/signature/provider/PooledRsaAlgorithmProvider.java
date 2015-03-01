package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.Signature;

public class PooledRsaAlgorithmProvider extends AbstractPooledAlgorithmProvider<Signature> {

	public PooledRsaAlgorithmProvider(String algorithm, PoolConfigurer poolConfigurer) {
		super(new RsaPoolableObject(algorithm), poolConfigurer);
	}
}
