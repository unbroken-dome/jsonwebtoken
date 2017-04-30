package org.unbrokendome.jsonwebtoken;

import com.fasterxml.jackson.databind.ObjectMapper;


public interface JwtProcessorBuilderBase<T extends JwtProcessorBase, B extends JwtProcessorBuilderBase<T, B>> {

    /**
     * Configures pooling for security algorithm objects.
     * <p>
     * If pooling is set up for signing and verification, a number of relevant algorithm instances (e.g.
     * {@link javax.crypto.Mac} or {@link java.security.Signature} for each supported algorithm will be
     * pre-constructed and kept in a pool. However, an exhausted pool does not block any signing or verification
     * requests; it will be grown as required.
     *
     * @param minSize the minimum number of instances that should be kept in the pool
     * @param maxIdle the maximum number of idle instances in the pool (any surplus will be released)
     * @return the current builder instance
     */
    B configurePool(int minSize, int maxIdle);

    /**
     * Configures a custom Jackson {@link ObjectMapper} to use for JSON serialization and deserialization.
     *
     * @param objectMapper the {@link ObjectMapper} instance to use
     * @return the current builder instance
     */
    B setObjectMapper(ObjectMapper objectMapper);

    T build();
}
