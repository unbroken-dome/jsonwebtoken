package org.unbrokendome.jsonwebtoken.encoding.payload;

import java.nio.ByteBuffer;

import org.unbrokendome.jsonwebtoken.BinaryData;

public class ByteBufferPayloadSerializer extends AbstractPayloadSerializer<ByteBuffer> {

	private static final ByteBufferPayloadSerializer INSTANCE = new ByteBufferPayloadSerializer();


	private ByteBufferPayloadSerializer() {
		super(ByteBuffer.class);
	}


	public static ByteBufferPayloadSerializer getInstance() {
		return INSTANCE;
	}


	@Override
	public BinaryData serialize(Object payload) {
		return BinaryData.of((ByteBuffer) payload);
	}


	@Override
	public ByteBuffer deserialize(BinaryData rawPayload) {
		return rawPayload.toByteBuffer();
	}
}
