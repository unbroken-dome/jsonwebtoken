package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;


public final class ByteBufferPayloadSerializer extends AbstractPayloadSerializer<ByteBuffer> {

    private static final ByteBufferPayloadSerializer INSTANCE = new ByteBufferPayloadSerializer();


    private ByteBufferPayloadSerializer() {
        super(ByteBuffer.class);
    }


    @Nonnull
    public static ByteBufferPayloadSerializer getInstance() {
        return INSTANCE;
    }


    @Override
    @Nonnull
    public BinaryData serialize(Object payload) {
        return BinaryData.of((ByteBuffer) payload);
    }


    @Override
    @Nonnull
    public ByteBuffer deserialize(BinaryData rawPayload) {
        return rawPayload.toByteBuffer();
    }
}
