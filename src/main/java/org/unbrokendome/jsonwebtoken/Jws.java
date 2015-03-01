package org.unbrokendome.jsonwebtoken;

import java.nio.ByteBuffer;

public interface Jws {

	BinaryData getHeader();


	default ByteBuffer getHeaderBytes() {
		return getHeader().toByteBuffer();
	}


	BinaryData getPayload();


	default ByteBuffer getPayloadBytes() {
		return getPayload().toByteBuffer();
	}


	BinaryData getSignature();


	default ByteBuffer getSignatureBytes() {
		return getSignature().toByteBuffer();
	}
}
