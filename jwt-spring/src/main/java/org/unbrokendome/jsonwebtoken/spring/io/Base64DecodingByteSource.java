package org.unbrokendome.jsonwebtoken.spring.io;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;


public final class Base64DecodingByteSource extends ByteSource {

    private final ByteSource encoded;


    public Base64DecodingByteSource(ByteSource encoded) {
        this.encoded = encoded;
    }


    public Base64DecodingByteSource(CharSource encoded, Charset charset) {
        this(encoded.asByteSource(charset));
    }


    @Override
    public byte[] read() throws IOException {
        return Base64.getMimeDecoder().decode(encoded.read());
    }


    @Override
    public InputStream openStream() throws IOException {
        InputStream encodedStream = encoded.openStream();
        return Base64.getDecoder().wrap(encodedStream);
    }
}
