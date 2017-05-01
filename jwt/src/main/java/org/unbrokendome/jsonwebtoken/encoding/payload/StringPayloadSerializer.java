package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public final class StringPayloadSerializer extends AbstractPayloadSerializer<String> {

    private static final StringPayloadSerializer UTF8_INSTANCE = new StringPayloadSerializer(StandardCharsets.UTF_8);
    private final Charset charset;


    private StringPayloadSerializer(Charset charset) {
        super(String.class);
        this.charset = charset;
    }


    public static StringPayloadSerializer getInstance() {
        return UTF8_INSTANCE;
    }


    public static StringPayloadSerializer getInstance(Charset charset) {
        return new StringPayloadSerializer(charset);
    }


    @Override
    public BinaryData serialize(Object payload) {
        return BinaryData.of(charset.encode((String) payload));
    }


    @Override
    public String deserialize(BinaryData rawPayload) {
        return charset.decode(rawPayload.toByteBuffer()).toString();
    }
}
