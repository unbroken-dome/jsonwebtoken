package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

@SuppressWarnings("null")
public abstract class AbstractPayloadSerializer<T> implements PayloadSerializer<T> {

	private final Class<T> payloadType;


	public AbstractPayloadSerializer(Class<T> payloadType) {
		this.payloadType = payloadType;
	}


	@Override
	public final boolean supports(Class<?> payloadType) {
		return payloadType == this.payloadType;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <U extends T> U deserialize(BinaryData rawPayload, Class<U> targetType) {
		return (U) deserialize(rawPayload);
	}


	protected abstract T deserialize(BinaryData rawPayload);
}
