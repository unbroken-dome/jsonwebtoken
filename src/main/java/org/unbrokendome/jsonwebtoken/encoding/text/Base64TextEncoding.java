package org.unbrokendome.jsonwebtoken.encoding.text;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;


public class Base64TextEncoding implements TextEncoding {

    private final Base64.Encoder base64Encoder;
    private final Base64.Decoder base64Decoder;

    public static final TextEncoding BASE64_URL = new Base64TextEncoding(Base64.getUrlEncoder(), Base64.getUrlDecoder());


    private Base64TextEncoding(Base64.Encoder base64Encoder, Base64.Decoder base64Decoder) {
        this.base64Encoder = base64Encoder;
        this.base64Decoder = base64Decoder;
    }


    @Override
    public Function<BinaryData, String> getEncoder() {
        return (BinaryData b) -> {
            ByteBuffer base64Bytes = base64Encoder.encode(b.toByteBuffer());
            return StandardCharsets.UTF_8.decode(base64Bytes).toString();
        };
    }


    @Override
    public Function<String, BinaryData> getDecoder() {
        return (String s) -> {
            ByteBuffer utf8Bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(s));
            return BinaryData.of(base64Decoder.decode(utf8Bytes));
        };
    }
}
