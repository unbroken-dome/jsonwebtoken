package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;


public interface PayloadDeserializer<T> {

    boolean supports(Class<?> payloadType);


    @Nonnull
    <U extends T> U deserialize(BinaryData rawPayload, Class<U> targetType);
}
