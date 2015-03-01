package org.unbrokendome.jsonwebtoken.encoding.payload;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.Test;
import org.unbrokendome.jsonwebtoken.BinaryData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

public class DefaultPayloadSerializerTest {

	private static final Map<String, Object> TEST_MAP = ImmutableMap.of("key1", "value1", "key2", 123, "key3", false);
	private static final String TEST_MAP_STRING = "{\"key1\":\"value1\",\"key2\":123,\"key3\":false}";
	private static final ByteBuffer TEST_MAP_BYTES = StandardCharsets.UTF_8.encode(TEST_MAP_STRING);
	private static final BinaryData TEST_MAP_DATA = BinaryData.of(TEST_MAP_BYTES);

	private PayloadSerializer<Object> serializer = new DefaultPayloadSerializer(new ObjectMapper());


	@Test
	public void testSerializeMap() {
		BinaryData bytes = serializer.serialize(TEST_MAP);
		assertThat(bytes, equalTo(TEST_MAP_DATA));
	}


	@Test
	public void testDeserializeMap() {
		Map<String, Object> map = serializer.deserialize(TEST_MAP_DATA, Map.class);
		assertThat(map, equalTo(TEST_MAP));
	}

}
