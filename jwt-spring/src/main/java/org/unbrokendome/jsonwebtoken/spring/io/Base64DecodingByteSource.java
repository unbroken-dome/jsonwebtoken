package org.unbrokendome.jsonwebtoken.spring.io;

import org.unbrokendome.jsonwebtoken.IOSupplier;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;


public final class Base64DecodingByteSource implements IOSupplier<byte[]> {

    private final IOSupplier<byte[]> encoded;


    public Base64DecodingByteSource(IOSupplier<byte[]> encoded) {
        this.encoded = encoded;
    }


    public Base64DecodingByteSource(IOSupplier<String> encoded, Charset charset) {
        this(() -> encoded.get().getBytes(charset));
    }


    @Override
    public byte[] get() throws IOException {
        byte[] encodedBytes = encoded.get();
        return Base64.getMimeDecoder().decode(encodedBytes);
    }
}
