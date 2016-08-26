package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

import java.util.Map;


public class DefaultJoseHeaderBuilder extends AbstractMapDataBuilder<JoseHeaderBuilder, JoseHeader> implements
        JoseHeaderBuilder {

    @Override
    protected JoseHeader buildFromMap(Map<String, Object> map) {
        return new DefaultJoseHeader(map);
    }
}
