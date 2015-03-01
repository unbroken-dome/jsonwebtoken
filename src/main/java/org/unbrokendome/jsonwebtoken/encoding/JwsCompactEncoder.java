package org.unbrokendome.jsonwebtoken.encoding;

import java.util.function.Function;

import org.unbrokendome.jsonwebtoken.BinaryData;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JwsCompactEncoder implements JwsEncoder {

	private static final char SEPARATOR = '.';

	private final Function<BinaryData, String> textEncoder;


	public JwsCompactEncoder(Function<BinaryData, String> textEncoder) {
		this.textEncoder = textEncoder;
	}


	@Override
	public String encode(BinaryData header, BinaryData payload, BinaryData signature) {
		try {
			return encodeHeader(header) + SEPARATOR + encodePayload(payload) + SEPARATOR
					+ encodeSignatureIfPresent(signature);
		}
		catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error encoding JWT", e);
		}
	}


	private String encodeHeader(BinaryData header) {
		return textEncoder.apply(header);
	}


	private String encodePayload(BinaryData payload) throws JsonProcessingException {
		return textEncoder.apply(payload);
	}


	private String encodeSignatureIfPresent(BinaryData signature) {
		return (signature != null) ? textEncoder.apply(signature) : "";
	}
}
