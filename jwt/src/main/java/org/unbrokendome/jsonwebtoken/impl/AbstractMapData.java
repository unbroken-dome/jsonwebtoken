package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;


abstract class AbstractMapData {

    private final Map<String, Object> map;


    AbstractMapData(Map<String, Object> map) {
        this.map = map;
    }


    @Nullable
    public Object get(String key) {
        return map.get(key);
    }


    @JsonValue
    @Nonnull
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
    @Nonnull
    public String toString() {
        return map.toString();
    }
}
