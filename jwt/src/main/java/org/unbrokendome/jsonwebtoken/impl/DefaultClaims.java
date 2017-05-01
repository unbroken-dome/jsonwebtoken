package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.unbrokendome.jsonwebtoken.Claims;

import java.util.Map;


public final class DefaultClaims extends AbstractMapData implements Claims {

    @JsonCreator
    public DefaultClaims(Map<String, Object> map) {
        super(map);
    }
}
