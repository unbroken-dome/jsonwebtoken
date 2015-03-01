package org.unbrokendome.jsonwebtoken.signature.impl;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

public final class NoneSignatureAlgorithm implements SignatureAlgorithm {

	private static final NoneSignatureAlgorithm INSTANCE = new NoneSignatureAlgorithm();


	private NoneSignatureAlgorithm() {
	}


	public static NoneSignatureAlgorithm getInstance() {
		return INSTANCE;
	}


	@Override
	public String getJwaName() {
		return "none";
	}


	@Override
	public Pair<Signer, Verifier> createSignerAndVerifier(Optional<PoolConfigurer> poolConfigurer) {
		return Pair.of(NoneSigner.getInstance(), NoneVerifier.getInstance());
	}


	@Override
	public Verifier createVerifier(Optional<PoolConfigurer> poolConfigurer) {
		return NoneVerifier.getInstance();
	}
}
