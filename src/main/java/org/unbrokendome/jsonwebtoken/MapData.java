package org.unbrokendome.jsonwebtoken;

import java.time.Instant;
import java.util.Map;

public interface MapData {

	Object get(String key);


	Map<String, Object> asMap();


	default String getString(String key) {
		return (String) get(key);
	}


	default Instant getInstant(String key) {
		Object value = get(key);
		if (value instanceof Instant) {
			return (Instant) value;
		}
		else if (value instanceof Number) {
			long seconds = ((Number) value).longValue();
			return Instant.ofEpochSecond(seconds);
		}
		else {
			throw new IllegalStateException("Cannot convert value " + value + " to Instant");
		}
	}
}
