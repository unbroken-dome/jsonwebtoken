package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

public interface PayloadSerializer<T> {

	boolean supports(Class<?> payloadType);


	BinaryData serialize(Object payload);


	<U extends T> U deserialize(BinaryData rawPayload, Class<U> targetType);
}
