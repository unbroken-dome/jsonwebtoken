package org.unbrokendome.jsonwebtoken.encoding.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import org.unbrokendome.jsonwebtoken.BinaryData;

import java.io.IOException;


public final class DefaultPayloadDeserializer implements PayloadDeserializer<Object> {

    private final ObjectMapper objectMapper;


    public DefaultPayloadDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public boolean supports(Class<?> payloadType) {
        return true;
    }


    @Override
    public <U> U deserialize(BinaryData rawPayload, Class<U> targetType) {
        try {
            return objectMapper.readValue(new ByteBufferBackedInputStream(rawPayload.toByteBuffer()), targetType);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing payload", e);
        }
    }
}
