package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;


abstract class AbstractPayloadSerializer<T> implements PayloadSerializer, PayloadDeserializer<T> {

    private final Class<T> payloadType;


    AbstractPayloadSerializer(Class<T> payloadType) {
        this.payloadType = payloadType;
    }


    @Override
    public final boolean supports(Class<?> payloadType) {
        return payloadType == this.payloadType;
    }


    @SuppressWarnings("unchecked")
    @Override
    @Nonnull
    public <U extends T> U deserialize(BinaryData rawPayload, Class<U> targetType) {
        return (U) deserialize(rawPayload);
    }


    @Nonnull
    protected abstract T deserialize(BinaryData rawPayload);
}
