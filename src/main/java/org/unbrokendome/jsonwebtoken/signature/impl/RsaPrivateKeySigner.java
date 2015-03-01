package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.Key;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import com.google.common.base.Preconditions;

public final class RsaPrivateKeySigner extends AbstractSigner<Signature> {

	public RsaPrivateKeySigner(AlgorithmProvider<Signature> provider) {
		super(provider);
	}


	@Override
	protected final BinaryData calculateSignature(BinaryData header, BinaryData payload, Key key)
			throws SignatureException {
		Preconditions.checkArgument(key instanceof PrivateKey, "Key must be a PrivateKey");

		byte[] sigBytes = doWithAlgorithm(sig -> {
			sig.initSign((PrivateKey) key);
			sig.update(header.toByteBuffer());
			sig.update(getEncodedSeparator());
			sig.update(payload.toByteBuffer());
			return sig.sign();
		});

		return BinaryData.of(sigBytes);
	}
}
