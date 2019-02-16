package org.unbrokendome.jsonwebtoken;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;


/**
 * Stores a blob of binary data.
 * <p>
 * This is essentially a wrapper over {@link ByteBuffer} to ensure immutability.
 */
public final class BinaryData {

    public static final BinaryData EMPTY = BinaryData.of(ByteBuffer.allocate(0));

    private final ByteBuffer buffer;


    private BinaryData(ByteBuffer buffer) {
        this.buffer = buffer;
    }


    public static BinaryData of(ByteBuffer buffer) {
        return new BinaryData(buffer.asReadOnlyBuffer());
    }


    public static BinaryData of(byte[] data) {
        return of(ByteBuffer.wrap(data));
    }


    public static BinaryData of(long... data) {
        ByteBuffer buffer = ByteBuffer.allocate(8 * data.length);
        buffer.asLongBuffer().put(data);
        buffer.rewind();
        return of(buffer);
    }


    public static BinaryData of(String s, Charset charset) {
        return of(charset.encode(s));
    }


    public static BinaryData ofUtf8(String s) {
        return of(s, StandardCharsets.UTF_8);
    }


    public int length() {
        return buffer.limit();
    }


    public ByteBuffer toByteBuffer() {
        return buffer.asReadOnlyBuffer();
    }


    public byte[] toByteArray() {
        byte[] data = new byte[buffer.limit()];
        buffer.asReadOnlyBuffer().get(data);
        return data;
    }


    public boolean isEmpty() {
        return buffer.limit() == 0;
    }


    @Override
    public boolean equals(Object obj) {
        return (this == obj) || (obj instanceof BinaryData && equals((BinaryData) obj));
    }


    private boolean equals(BinaryData other) {
        return buffer.equals(other.buffer);
    }


    @Override
    public int hashCode() {
        return Objects.hash(buffer);
    }


    @Override
    public String toString() {
        ByteBuffer base64Bytes = Base64.getEncoder().encode(toByteBuffer());
        return "{binary: " + StandardCharsets.UTF_8.decode(base64Bytes) + "}";
    }
}
