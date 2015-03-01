package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.crypto.Mac;

public final class PooledMacAlgorithmProvider extends AbstractPooledAlgorithmProvider<Mac> {

	public PooledMacAlgorithmProvider(String algorithm, PoolConfigurer poolConfigurer) {
		super(new MacPoolableObject(algorithm), poolConfigurer);
	}
}
