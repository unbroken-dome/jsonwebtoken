package org.unbrokendome.jsonwebtoken.spring.io;

import com.google.common.base.Optional;
import com.google.common.io.ByteSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;


public final class ResourceByteSourceAdapter extends ByteSource {

    private final Resource resource;


    public ResourceByteSourceAdapter(Resource resource) {
        this.resource = resource;
    }


    @Override
    public Optional<Long> sizeIfKnown() {
        try {
            return Optional.of(resource.contentLength());
        } catch (IOException ex) {
            return Optional.absent();
        }
    }


    @Override
    public InputStream openStream() throws IOException {
        return resource.getInputStream();
    }
}
