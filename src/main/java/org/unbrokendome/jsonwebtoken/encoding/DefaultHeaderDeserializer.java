package org.unbrokendome.jsonwebtoken.encoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeader;

import java.io.IOException;
import java.util.Map;


public class DefaultHeaderDeserializer implements HeaderDeserializer {

    private final ObjectMapper objectMapper;


    public DefaultHeaderDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public JoseHeader deserialize(BinaryData data) throws JwtMalformedTokenException {
        try {
            Map<String, Object> headerValues = objectMapper.readValue(
                    new ByteBufferBackedInputStream(data.toByteBuffer()), Map.class);
            return new DefaultJoseHeader(headerValues);
        } catch (IOException e) {
            throw new JwtMalformedTokenException("Error deserializing JWS header", e);
        }
    }
}
