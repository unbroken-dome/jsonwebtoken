package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;


public interface PayloadDeserializer<T> {

    boolean supports(Class<?> payloadType);


    <U extends T> U deserialize(BinaryData rawPayload, Class<U> targetType);
}
