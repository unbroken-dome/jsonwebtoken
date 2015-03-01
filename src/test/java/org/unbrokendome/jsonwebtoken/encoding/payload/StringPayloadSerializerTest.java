package org.unbrokendome.jsonwebtoken.encoding.payload;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;

public class StringPayloadSerializerTest {

	private static final String TEST_STRING = "Lorem ipsum dolor sit amet";
	private static final BinaryData TEST_DATA = BinaryData.ofUtf8(TEST_STRING);

	private PayloadSerializer<String> serializer = StringPayloadSerializer.getInstance();


	@Test
	public void testSerialize() {
		BinaryData result = serializer.serialize(TEST_STRING);
		assertThat(result, equalTo(TEST_DATA));
	}


	@Test
	public void testDeserialize() {
		String result = serializer.deserialize(TEST_DATA, String.class);
		assertThat(result, equalTo(TEST_STRING));
	}
}
