package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.Jws;

public class DefaultJws implements Jws {

	private final BinaryData header, payload, signature;


	public DefaultJws(BinaryData header, BinaryData payload, BinaryData signature) {
		this.header = header;
		this.payload = payload;
		this.signature = signature;
	}


	@Override
	public BinaryData getHeader() {
		return header;
	}


	@Override
	public BinaryData getPayload() {
		return payload;
	}


	@Override
	public BinaryData getSignature() {
		return signature;
	}
}
