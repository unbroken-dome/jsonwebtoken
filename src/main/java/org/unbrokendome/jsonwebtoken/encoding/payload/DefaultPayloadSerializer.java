package org.unbrokendome.jsonwebtoken.encoding.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.BinaryData;


public final class DefaultPayloadSerializer implements PayloadSerializer {

    private final ObjectMapper objectMapper;


    public DefaultPayloadSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public boolean supports(Class<?> payloadType) {
        return true;
    }


    @Override
    public BinaryData serialize(Object payload) {
        try {
            byte[] payloadBytes = objectMapper.writeValueAsBytes(payload);
            return BinaryData.of(payloadBytes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing payload", e);
        }
    }
}
