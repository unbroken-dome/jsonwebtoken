package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.MapData;
import org.unbrokendome.jsonwebtoken.MapDataBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class AbstractMapDataBuilder<TBuilder extends MapDataBuilder<TBuilder, TResult>, TResult extends MapData>
        implements MapDataBuilder<TBuilder, TResult> {

    private final Map<String, Object> map = new LinkedHashMap<>();


    @Override
    @Nullable
    public Object get(String key) {
        return map.get(key);
    }


    @Override
    @Nonnull
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(new HashMap<>(map));
    }


    @SuppressWarnings("unchecked")
    @Override
    @Nonnull
    public final TBuilder set(String key, Object value) {
        map.put(key, value);
        return (TBuilder) this;
    }


    @SuppressWarnings("unchecked")
    @Override
    @Nonnull
    public final TBuilder set(Map<String, ?> values) {
        map.putAll(values);
        return (TBuilder) this;
    }


    @Override
    @Nonnull
    public final TResult build() {
        return buildFromMap(asMap());
    }


    @Nonnull
    protected abstract TResult buildFromMap(Map<String, Object> map);
}
