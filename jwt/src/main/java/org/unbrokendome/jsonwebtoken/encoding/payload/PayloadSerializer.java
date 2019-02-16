package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;


public interface PayloadSerializer {

    boolean supports(Class<?> payloadType);


    @Nonnull
    BinaryData serialize(Object payload);
}
