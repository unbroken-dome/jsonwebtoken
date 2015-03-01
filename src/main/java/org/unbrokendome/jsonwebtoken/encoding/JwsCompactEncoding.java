package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.encoding.text.Base64TextEncoding;
import org.unbrokendome.jsonwebtoken.encoding.text.TextEncoding;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwsCompactEncoding implements JwsEncoding {

	private final TextEncoding textEncoding = Base64TextEncoding.BASE64_URL;
	private final HeaderSerializer headerSerializer;
	private final HeaderDeserializer headerDeserializer;
	private final JwsEncoder encoder = new JwsCompactEncoder(textEncoding.getEncoder());
	private final JwsDecoder decoder = new JwsCompactDecoder(textEncoding.getDecoder());


	public JwsCompactEncoding(ObjectMapper objectMapper) {
		headerSerializer = new DefaultHeaderSerializer(objectMapper);
		headerDeserializer = new DefaultHeaderDeserializer(objectMapper);
	}


	@Override
	public HeaderSerializer getHeaderSerializer() {
		return headerSerializer;
	}


	@Override
	public HeaderDeserializer getHeaderDeserializer() {
		return headerDeserializer;
	}


	@Override
	public JwsEncoder getEncoder() {
		return encoder;
	}


	@Override
	public JwsDecoder getDecoder() {
		return decoder;
	}
}
