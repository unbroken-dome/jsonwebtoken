package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

import javax.annotation.Nonnull;
import java.util.Map;


public final class DefaultJoseHeaderBuilder
        extends AbstractMapDataBuilder<JoseHeaderBuilder, JoseHeader>
        implements JoseHeaderBuilder {

    @Override
    @Nonnull
    protected JoseHeader buildFromMap(Map<String, Object> map) {
        return new DefaultJoseHeader(map);
    }
}
