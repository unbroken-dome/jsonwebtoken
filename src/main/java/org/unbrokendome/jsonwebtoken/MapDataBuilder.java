package org.unbrokendome.jsonwebtoken;

import java.time.Instant;
import java.util.Map;

public interface MapDataBuilder<TBuilder extends MapDataBuilder<TBuilder, TResult>,
                                TResult extends MapData>
		extends MapData {

	TBuilder set(String key, Object value);


	TBuilder set(Map<String, Object> values);


	TResult build();


	default TBuilder set(String key, Instant value) {
		return set(key, value.getEpochSecond());
	}
}
