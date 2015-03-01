package org.unbrokendome.jsonwebtoken.encoding.payload;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.ByteBuffer;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;

public class ByteBufferPayloadSerializerTest {

	private static final byte[] TEST_DATA_BYTES = new byte[] { 18, 119, 101, -110, 23, 1, 53, 15, -27, 54, 21, 31, -33,
			-28, 90, 24, 7, 56, -44, 120 };
	private static final BinaryData TEST_DATA = BinaryData.of(TEST_DATA_BYTES);

	private PayloadSerializer<ByteBuffer> serializer = ByteBufferPayloadSerializer.getInstance();


	@Test
	public void testSerialize() {
		BinaryData result = serializer.serialize(ByteBuffer.wrap(TEST_DATA_BYTES));
		assertThat(result, equalTo(TEST_DATA));
	}


	@Test
	public void testDeserialize() {
		ByteBuffer result = serializer.deserialize(TEST_DATA, ByteBuffer.class);
		assertThat(result, equalTo(TEST_DATA.toByteBuffer()));
	}
}
