package org.unbrokendome.jsonwebtoken.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;


public class DefaultHeaderSerializer implements HeaderSerializer {

    private final ObjectMapper objectMapper;


    public DefaultHeaderSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public BinaryData serialize(JoseHeader header) {
        try {
            byte[] headerBytes = objectMapper.writeValueAsBytes(header.asMap());
            return BinaryData.of(headerBytes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error encoding JWS header", e);
        }
    }
}
