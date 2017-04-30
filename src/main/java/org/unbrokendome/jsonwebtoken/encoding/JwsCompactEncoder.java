package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.encoding.text.Base64TextEncoding;

import javax.annotation.Nullable;
import java.util.function.Function;


public final class JwsCompactEncoder implements JwsEncoder {

    private static final char SEPARATOR = '.';

    private final Function<BinaryData, String> textEncoder;


    public JwsCompactEncoder(Function<BinaryData, String> textEncoder) {
        this.textEncoder = textEncoder;
    }


    public JwsCompactEncoder() {
        this(Base64TextEncoding.BASE64_URL.getEncoder());
    }


    @Override
    public String encode(BinaryData header, BinaryData payload, @Nullable BinaryData signature) {
        return encodeHeader(header)
                + SEPARATOR
                + encodePayload(payload)
                + SEPARATOR
                + encodeSignatureIfPresent(signature);
    }


    private String encodeHeader(BinaryData header) {
        return textEncoder.apply(header);
    }


    private String encodePayload(BinaryData payload) {
        return textEncoder.apply(payload);
    }


    private String encodeSignatureIfPresent(@Nullable BinaryData signature) {
        return (signature != null) ? textEncoder.apply(signature) : "";
    }
}
