package org.unbrokendome.jsonwebtoken.spring.io;

import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.unbrokendome.jsonwebtoken.IOSupplier;

import java.io.IOException;
import java.io.InputStream;


public class ResourceBinaryDataSupplier implements IOSupplier<byte[]> {

    private final Resource resource;


    public ResourceBinaryDataSupplier(Resource resource) {
        this.resource = resource;
    }


    @Override
    public byte[] get() throws IOException {
        try (InputStream input = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(input);
        }
    }
}
