package org.unbrokendome.jsonwebtoken.encoding;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultHeaderSerializerTest {

	private static final JoseHeader TEST_HEADER = new DefaultJoseHeaderBuilder()
			.setAlgorithm("none")
			.setType("JWT")
			.build();

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final HeaderSerializer serializer = new DefaultHeaderSerializer(objectMapper);


	@Test
	public void testSerialize() throws Exception {
		String expected = objectMapper.writeValueAsString(TEST_HEADER);

		BinaryData bytes = serializer.serialize(TEST_HEADER);

		assertThat(bytes, equalTo(BinaryData.ofUtf8(expected)));
	}
}
