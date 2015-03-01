package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.Signature;
import java.util.Optional;

import javax.crypto.Mac;

public final class AlgorithmProviders {

	public static AlgorithmProvider<Mac> mac(String algorithm, Optional<PoolConfigurer> poolConfigurer) {
		return poolConfigurer.isPresent() ? pooledMac(algorithm, poolConfigurer.get()) : simpleMac(algorithm);
	}


	public static AlgorithmProvider<Mac> simpleMac(String algorithm) {
		return new MacAlgorithmProvider(algorithm);
	}


	public static AlgorithmProvider<Mac> pooledMac(String algorithm, PoolConfigurer poolConfigurer) {
		return new PooledMacAlgorithmProvider(algorithm, poolConfigurer);
	}


	public static AlgorithmProvider<Signature> rsa(String algorithm, Optional<PoolConfigurer> poolConfigurer) {
		return poolConfigurer.isPresent() ? pooledRsa(algorithm, poolConfigurer.get()) : simpleRsa(algorithm);
	}


	public static AlgorithmProvider<Signature> simpleRsa(String algorithm) {
		return new RsaAlgorithmProvider(algorithm);
	}


	public static AlgorithmProvider<Signature> pooledRsa(String algorithm, PoolConfigurer poolConfigurer) {
		return new PooledRsaAlgorithmProvider(algorithm, poolConfigurer);
	}
}
