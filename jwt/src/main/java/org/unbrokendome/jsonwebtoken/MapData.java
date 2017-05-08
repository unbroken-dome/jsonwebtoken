package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Base interface for objects that are collections of key-value pairs.
 */
public interface MapData {

    /**
     * Gets the value for the given key.
     *
     * @param key the key
     * @return the associated value, or <code>null</code> if no entry with the given key is present
     */
    @Nullable
    Object get(String key);

    /**
     * Returns a {@link Map} containing all the entries of this instance.
     *
     * @return a {@link Map} equivalent to this instance
     */
    Map<String, Object> asMap();


    /**
     * Returns a value as a string.
     * <p>
     * If the entry is present but not a string, it is converted using the {@link Object#toString() toString} method.
     *
     * @param key the key
     * @return the associated value as a string, or <code>null</code> if no entry with the given key is present
     */
    @Nullable
    default String getString(String key) {
        return Objects.toString(get(key), null);
    }


    /**
     * Returns a value as a set of strings.
     *
     * <p>A JSON array will be converted to a Set, with each element converted to String using the
     * {@link Object#toString()} method. Nested JSON arrays are not treated specially.
     *
     * <p>If the value is not a JSON array but a simple value (e.g. a string), it is wrapped in a
     * one-element Set.</p>
     *
     * @param key the key
     * @return the associated values as a {@link Set} of strings or <code>null</code> if no entry with the given
     *         key is present
     */
    @Nullable
    default Set<String> getStringSet(String key) {
        Object value = get(key);
        if (value instanceof Collection) {
            return ((Collection<?>) value).stream()
                    .map(Objects::toString)
                    .collect(Collectors.toSet());
        } else if (value != null) {
            String stringValue = Objects.toString(value);
            return Collections.singleton(stringValue);
        } else {
            return null;
        }
    }


    /**
     * Returns a value as an instant in time.
     * <p>
     * Instants must be stored as numbers in the map, and are converted using the {@link Instant#ofEpochSecond(long)}
     * method.
     *
     * @param key the key
     * @return the associated value as an {@link Instant}, or <code>null</code> if no entry with the given
     * key is present
     * @throws IllegalStateException if the value is present, but cannot be converted to an {@link Instant}
     */
    @Nullable
    default Instant getInstant(String key) {
        Object value = get(key);
        if (value instanceof Instant) {
            return (Instant) value;
        } else if (value instanceof Number) {
            long seconds = ((Number) value).longValue();
            return Instant.ofEpochSecond(seconds);
        } else if (value == null) {
            return null;
        } else {
            throw new IllegalStateException("Cannot convert value " + value + " to Instant");
        }
    }
}
