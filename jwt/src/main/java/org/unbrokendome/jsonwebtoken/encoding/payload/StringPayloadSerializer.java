package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public final class StringPayloadSerializer extends AbstractPayloadSerializer<String> {

    private static final StringPayloadSerializer UTF8_INSTANCE = new StringPayloadSerializer(StandardCharsets.UTF_8);
    private final Charset charset;


    private StringPayloadSerializer(Charset charset) {
        super(String.class);
        this.charset = charset;
    }


    @Nonnull
    public static StringPayloadSerializer getInstance() {
        return UTF8_INSTANCE;
    }


    @Nonnull
    public static StringPayloadSerializer getInstance(Charset charset) {
        return new StringPayloadSerializer(charset);
    }


    @Override
    @Nonnull
    public BinaryData serialize(Object payload) {
        return BinaryData.of(charset.encode((String) payload));
    }


    @Override
    @Nonnull
    public String deserialize(BinaryData rawPayload) {
        return charset.decode(rawPayload.toByteBuffer()).toString();
    }
}
