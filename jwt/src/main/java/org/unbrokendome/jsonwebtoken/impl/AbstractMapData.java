package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.Objects;


abstract class AbstractMapData {

    private final Map<String, Object> map;


    protected AbstractMapData(Map<String, Object> map) {
        this.map = map;
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
        return Objects.hash(map);
    }


    @Override
    public String toString() {
        return map.toString();
    }
}
