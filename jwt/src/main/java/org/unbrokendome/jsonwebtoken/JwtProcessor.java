package org.unbrokendome.jsonwebtoken;

/**
 * Encodes and decodes JSON Web Tokens.
 * <p>
 * You can construct {@link JwtProcessor} instances by first creating a {@link JwtProcessorBuilder} using the
 * {@link Jwt#processor()} method, then configuring it and calling {@link JwtProcessorBuilder#build() build()}:
 *
 * <pre>
 *     JwtProcessor processor =
 *         Jwt.processor()
 *             // ... call methods on the builder ...
 *             .build();
 * </pre>
 *
 * <p>
 * JWT payloads are typically {@link Claims} objects when JWTs are used in an authentication/authorization scenario.
 * However, <code>String</code> and {@link java.nio.ByteBuffer ByteBuffer} payloads are supported as well, and you
 * can register any custom payload serializer using the {@link JwtProcessorBuilder#serializePayloadWith} method.
 *
 * <p>
 * All {@link JwtProcessor} instances are immutable and safe for use by concurrent threads.
 */
public interface JwtProcessor extends JwtEncodingProcessor, JwtDecodingProcessor {

}
