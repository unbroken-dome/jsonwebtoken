package org.unbrokendome.jsonwebtoken.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;

import javax.annotation.Nonnull;


public final class DefaultHeaderSerializer implements HeaderSerializer {

    private final ObjectMapper objectMapper;


    public DefaultHeaderSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    @Nonnull
    public BinaryData serialize(JoseHeader header) {
        try {
            byte[] headerBytes = objectMapper.writeValueAsBytes(header.asMap());
            return BinaryData.of(headerBytes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error encoding JWS header", e);
        }
    }
}
