package org.unbrokendome.jsonwebtoken.encoding.payload;

import java.io.IOException;

import org.unbrokendome.jsonwebtoken.BinaryData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

@SuppressWarnings("null")
public class DefaultPayloadSerializer implements PayloadSerializer<Object> {

	private final ObjectMapper objectMapper;


	public DefaultPayloadSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}


	@Override
	public boolean supports(Class<?> payloadType) {
		return true;
	}


	@Override
	public BinaryData serialize(Object payload) {
		try {
			byte[] payloadBytes = objectMapper.writeValueAsBytes(payload);
			return BinaryData.of(payloadBytes);
		}
		catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error serializing payload", e);
		}
	}


	@Override
	public <U> U deserialize(BinaryData rawPayload, Class<U> targetType) {
		try {
			return objectMapper.readValue(new ByteBufferBackedInputStream(rawPayload.toByteBuffer()), targetType);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Error deserializing payload", e);
		}
	}
}
