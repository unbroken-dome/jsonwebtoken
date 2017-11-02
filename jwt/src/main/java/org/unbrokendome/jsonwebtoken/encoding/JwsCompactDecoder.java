package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.Jws;
import org.unbrokendome.jsonwebtoken.encoding.text.Base64TextEncoding;
import org.unbrokendome.jsonwebtoken.impl.DefaultJws;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;


public final class JwsCompactDecoder implements JwsDecoder {

    private static final char SEPARATOR = '.';

    private final Function<String, BinaryData> textDecoder;


    public JwsCompactDecoder(Function<String, BinaryData> textDecoder) {
        this.textDecoder = textDecoder;
    }


    public JwsCompactDecoder() {
        this(Base64TextEncoding.BASE64_URL.getDecoder());
    }


    @Override
    public Jws decode(String encoded) throws JwtMalformedTokenException {
        StringTokenizer tokenizer = new StringTokenizer(encoded, String.valueOf(SEPARATOR));
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<String> parts = (List) Collections.list(tokenizer);

        // For the NONE algorithm, the token ends with a dot - the signature part is empty. StringTokenizer won't
        // add a token for this, so let's add an empty third part manually
        if (parts.size() == 2 && encoded.endsWith(".")) {
            parts.add("");
        }

        if (parts.size() != 3) {
            throw new JwtMalformedTokenException("Token must contain exactly 3 parts separated by a period");
        }

        return new DefaultJws(decodeHeader(parts.get(0)), decodePayload(parts.get(1)), decodeSignature(parts.get(2)));
    }


    private BinaryData decodeHeader(String encoded) throws JwtMalformedTokenException {
        return decodeText(encoded);
    }


    private BinaryData decodePayload(String encoded) throws JwtMalformedTokenException {
        return decodeText(encoded);
    }


    private BinaryData decodeSignature(String encoded) throws JwtMalformedTokenException {
        return !encoded.isEmpty() ? decodeText(encoded) : BinaryData.EMPTY;
    }


    private BinaryData decodeText(String encoded) throws JwtMalformedTokenException {
        try {
            return textDecoder.apply(encoded);
        } catch (IllegalArgumentException e) {
            throw new JwtMalformedTokenException("Error while decoding token", e);
        }
    }
}
