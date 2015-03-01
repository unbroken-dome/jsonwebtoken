package org.unbrokendome.jsonwebtoken.signature.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import com.google.common.base.Preconditions;

public final class RsaPublicKeyVerifier extends SecurityAlgorithmSupport<Signature> implements Verifier {

	private static final byte[] ENCODED_SEPARATOR = ".".getBytes(StandardCharsets.UTF_8);


	public RsaPublicKeyVerifier(AlgorithmProvider<Signature> provider) {
		super(provider);
	}


	@Override
	public void verify(BinaryData header, BinaryData payload, BinaryData signature, Key key)
			throws JwsSignatureException {
		Preconditions.checkArgument(key instanceof PublicKey, "Key must be a PublicKey");

		try {
			if (!doVerify(header, payload, signature, (PublicKey) key)) {
				throw new JwsSignatureMismatchException("Incorrect signature");
			}
		}
		catch (SignatureException e) {
			throw new JwsSignatureException("Bad signature", e);
		}
	}


	private boolean doVerify(BinaryData header, BinaryData payload, BinaryData signature, PublicKey publicKey)
			throws SignatureException {
		return doWithAlgorithm(sig -> {
			sig.initVerify(publicKey);

			sig.update(header.toByteBuffer());
			sig.update(ENCODED_SEPARATOR);
			sig.update(header.toByteBuffer());

			return sig.verify(signature.toByteArray());
		});
	}
}
