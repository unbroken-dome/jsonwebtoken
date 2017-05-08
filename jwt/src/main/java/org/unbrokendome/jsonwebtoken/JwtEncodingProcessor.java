package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;


/**
 * Encodes objects into JSON Web Tokens.
 *
 * <p>
 * You can construct {@link JwtEncodingProcessor} instances by first creating a {@link JwtProcessorBuilder} using the
 * {@link Jwt#processor()} and {@link JwtProcessorBuilder#encodeOnly() encodeOnly()} methods, then configuring it and
 * calling {@link JwtEncodeOnlyProcessorBuilder#build() build()}:
 *
 * <pre>
 *     JwtEncodingProcessor processor =
 *         Jwt.processor().encodeOnly()
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
 * All {@link JwtEncodingProcessor} instances are immutable and safe for use by concurrent threads.
 */
public interface JwtEncodingProcessor extends JwtProcessorBase {

    /**
     * Creates a new JSON Web Token from the given payload.
     * <p>
     * The payload is typically a {@link Claims} object, but can be anything that this JWT processor knows how
     * to serialize. You can register custom payload serializers during configuration with the
     * {@link JwtProcessorBuilder#serializePayloadWith} method.
     *
     * @param payload the payload to encode as a JSON Web Token
     * @return the encoded JSON Web Token as a string
     * @throws JwsSignatureException if the signature for the token cannot be created
     */
    String encode(Object payload) throws JwsSignatureException;


    /**
     * Creates a new JSON Web Token from the given payload.
     *
     * <p>
     * This is the same as {@link #encode(Object)}, but wraps any {@link JwsSignatureException} in an unchecked
     * {@link IllegalStateException}.
     *
     * @param payload the payload to encode as a JSON Web Token
     * @return the encoded JSON Web Token as a string
     * @throws IllegalStateException if the signature for the token cannot be created
     */
    default String encodeUnchecked(Object payload) {
        try {
            return encode(payload);
        } catch (JwsSignatureException ex) {
            throw new IllegalStateException("Error creating signature for JSON Web Token", ex);
        }
    }
}
