package org.unbrokendome.jsonwebtoken.encoding.payload;

import org.unbrokendome.jsonwebtoken.BinaryData;


public interface PayloadSerializer {

    boolean supports(Class<?> payloadType);


    BinaryData serialize(Object payload);
}
