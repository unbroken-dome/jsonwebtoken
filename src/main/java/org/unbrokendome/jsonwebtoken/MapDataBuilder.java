package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nonnull;
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
    @Nonnull
    TBuilder set(String key, Object value);

    /**
     * Sets key-value entries from the given {@link Map}.
     * @param values a map of key-value entries
     * @return the current builder instance
     */
    @Nonnull
    TBuilder set(Map<String, ?> values);

    @Nonnull
    TResult build();

    /**
     * Sets a value as an {@link Instant}.
     *
     * <p>The instant is converted to a number using the {@link Instant#getEpochSecond()}</p> method,
     * which complies with the <em>NumericDate</em> from
     * <a href="https://tools.ietf.org/html/rfc7519#section-2">RFC 7519</a>.
     *
     * @param key the key
     * @param value the value as an {@link Instant}
     * @return the current builder instance
     */
    @Nonnull
    default TBuilder set(String key, Instant value) {
        return set(key, value.getEpochSecond());
    }
}
