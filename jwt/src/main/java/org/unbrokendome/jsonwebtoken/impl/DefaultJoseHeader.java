package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.JoseHeader;

import java.util.Map;


public final class DefaultJoseHeader extends AbstractMapData implements JoseHeader {

    public DefaultJoseHeader(Map<String, Object> map) {
        super(map);
    }
}
