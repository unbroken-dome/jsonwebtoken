package org.unbrokendome.jsonwebtoken.encoding;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultHeaderDeserializerTest {

	private static final JoseHeader TEST_HEADER = new DefaultJoseHeaderBuilder()
			.setAlgorithm("none")
			.setType("JWT")
			.build();

	private HeaderDeserializer deserializer = new DefaultHeaderDeserializer(new ObjectMapper());


	@Test
	public void testDeserialize() throws Exception {
		String headerJson = "{\"alg\":\"none\",\"typ\":\"JWT\"}";
		JoseHeader header = deserializer.deserialize(BinaryData.ofUtf8(headerJson));

		assertThat(header, equalTo(TEST_HEADER));
	}


	@Test(expected = JwtMalformedTokenException.class)
	public void testDeserializeThrowsOnMalformedInput() throws Exception {
		deserializer.deserialize(BinaryData.ofUtf8("qr9etug4ehß9345"));
	}
}
