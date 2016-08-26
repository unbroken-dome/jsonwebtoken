package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;


public abstract class AbstractMapData {

    private final Map<String, Object> map;


    public AbstractMapData(Map<String, Object> map) {
        this.map = ImmutableMap.copyOf(map);
    }


    public Object get(String key) {
        return map.get(key);
    }


    @JsonValue
    public Map<String, Object> asMap() {
        return map;
    }


    @Override
    public boolean equals(Object obj) {
        return (this == obj) || (obj != null && obj.getClass() == this.getClass() && equals((AbstractMapData) obj));
    }


    private boolean equals(AbstractMapData other) {
        return map.equals(other.map);
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(map).toHashCode();
    }


    @Override
    public String toString() {
        return map.toString();
    }
}
