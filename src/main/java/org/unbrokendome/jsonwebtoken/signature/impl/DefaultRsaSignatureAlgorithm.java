package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.Signature;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProviders;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

public class DefaultRsaSignatureAlgorithm extends AbstractSignatureAlgorithm {

	public DefaultRsaSignatureAlgorithm(String jwaName, String jcaName) {
		super(jwaName, jcaName);
	}


	@Override
	public Pair<Signer, Verifier> createSignerAndVerifier(Optional<PoolConfigurer> poolConfigurer) {
		AlgorithmProvider<Signature> provider = AlgorithmProviders.rsa(getJcaName(), poolConfigurer);
		Signer signer = new RsaPrivateKeySigner(provider);
		Verifier verifier = new RsaFlexibleVerifier(provider, signer);

		return Pair.of(signer, verifier);
	}


	@Override
	public Verifier createVerifier(Optional<PoolConfigurer> poolConfigurer) {
		return new RsaPublicKeyVerifier(AlgorithmProviders.rsa(getJcaName(), poolConfigurer));
	}
}
