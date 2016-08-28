package org.unbrokendome.jsonwebtoken;

import java.time.Instant;
import java.util.Map;


/**
 * Base interface for builders of {@link MapData} objects.
 *
 * <code>MapDataBuilder</code> also implements {@link MapData} itself so the current entries can be queried.
 *
 * @param <TBuilder> the type of builder that implements this interface
 * @param <TResult> the type of result that will be constructed by this builder
 */
public interface MapDataBuilder<TBuilder extends MapDataBuilder<TBuilder, TResult>,
        TResult extends MapData>
        extends MapData {

    /**
     * Sets a single key-value entry.
     *
     * @param key the key
     * @param value the value
     * @return the current builder instance
     */
    TBuilder set(String key, Object value);


    TBuilder set(Map<String, Object> values);


    TResult build();


    default TBuilder set(String key, Instant value) {
        return set(key, value.getEpochSecond());
    }
}
