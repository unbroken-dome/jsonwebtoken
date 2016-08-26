package org.unbrokendome.jsonwebtoken.encoding;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.Jws;
import org.unbrokendome.jsonwebtoken.impl.DefaultJws;

import java.util.List;
import java.util.function.Function;


public class JwsCompactDecoder implements JwsDecoder {

    private static final char SEPARATOR = '.';

    private final Splitter splitter = Splitter.on(SEPARATOR);
    private final Function<String, BinaryData> textDecoder;


    public JwsCompactDecoder(Function<String, BinaryData> textDecoder) {
        this.textDecoder = textDecoder;
    }


    @Override
    public Jws decode(String encoded) throws JwtMalformedTokenException {
        List<String> parts = splitter.splitToList(encoded);

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
        return StringUtils.isNotEmpty(encoded) ? decodeText(encoded) : BinaryData.EMPTY;
    }


    private BinaryData decodeText(String encoded) throws JwtMalformedTokenException {
        try {
            return textDecoder.apply(encoded);
        } catch (IllegalArgumentException e) {
            throw new JwtMalformedTokenException("Error while decoding token", e);
        }
    }
}
