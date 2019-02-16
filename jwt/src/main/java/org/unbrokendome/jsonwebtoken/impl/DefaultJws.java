package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.Jws;

import javax.annotation.Nonnull;


public final class DefaultJws implements Jws {

    private final BinaryData header, payload, signature;


    public DefaultJws(BinaryData header, BinaryData payload, BinaryData signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }


    @Override
    @Nonnull
    public BinaryData getHeader() {
        return header;
    }


    @Override
    @Nonnull
    public BinaryData getPayload() {
        return payload;
    }


    @Override
    @Nonnull
    public BinaryData getSignature() {
        return signature;
    }
}
