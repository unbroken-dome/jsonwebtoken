package org.unbrokendome.jsonwebtoken;

import java.io.IOException;


/**
 * Represents a supplier of results which involves some I/O operation.
 *
 * <p>Similar to {@link java.util.function.Supplier}, but the {@link #get()} method may throw an
 * {@link IOException}.
 *
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface IOSupplier<T> {

    T get() throws IOException;
}
