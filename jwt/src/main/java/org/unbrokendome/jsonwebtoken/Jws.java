package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;


public interface Jws {

    @Nonnull
    BinaryData getHeader();


    @Nonnull
    default ByteBuffer getHeaderBytes() {
        return getHeader().toByteBuffer();
    }


    @Nonnull
    BinaryData getPayload();


    @Nonnull
    default ByteBuffer getPayloadBytes() {
        return getPayload().toByteBuffer();
    }


    @Nonnull
    BinaryData getSignature();


    @Nonnull
    default ByteBuffer getSignatureBytes() {
        return getSignature().toByteBuffer();
    }
}
