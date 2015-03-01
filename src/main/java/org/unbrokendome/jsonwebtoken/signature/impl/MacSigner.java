package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.Key;

import javax.crypto.Mac;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

public class MacSigner extends AbstractSigner<Mac> {

	public MacSigner(AlgorithmProvider<Mac> macProvider) {
		super(macProvider);
	}


	@Override
	protected BinaryData calculateSignature(BinaryData header, BinaryData payload, Key key) {
		byte[] signatureBytes = doWithAlgorithmSafely(mac -> {
			mac.init(key);
			mac.update(header.toByteBuffer());
			mac.update(getEncodedSeparator());
			mac.update(payload.toByteBuffer());
			return mac.doFinal();
		});

		return BinaryData.of(signatureBytes);
	}
}
