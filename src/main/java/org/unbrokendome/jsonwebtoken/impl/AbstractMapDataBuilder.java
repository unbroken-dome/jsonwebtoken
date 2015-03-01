package org.unbrokendome.jsonwebtoken.impl;

import java.util.HashMap;
import java.util.Map;

import org.unbrokendome.jsonwebtoken.MapData;
import org.unbrokendome.jsonwebtoken.MapDataBuilder;

import com.google.common.collect.ImmutableMap;

public abstract class AbstractMapDataBuilder<TBuilder extends MapDataBuilder<TBuilder, TResult>, TResult extends MapData>
		implements MapDataBuilder<TBuilder, TResult> {

	private final Map<String, Object> map = new HashMap<>();


	@Override
	public Object get(String key) {
		return map.get(key);
	}


	@Override
	public Map<String, Object> asMap() {
		return ImmutableMap.copyOf(map);
	}


	@SuppressWarnings("unchecked")
	@Override
	public final TBuilder set(String key, Object value) {
		map.put(key, value);
		return (TBuilder) this;
	}


	@SuppressWarnings("unchecked")
	@Override
	public final TBuilder set(Map<String, Object> values) {
		map.putAll(values);
		return (TBuilder) this;
	}


	@Override
	public final TResult build() {
		return buildFromMap(asMap());
	}


	protected abstract TResult buildFromMap(Map<String, Object> map);
}
