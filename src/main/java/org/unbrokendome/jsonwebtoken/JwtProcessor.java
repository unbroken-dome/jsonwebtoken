package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.impl.DefaultClaims;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;


/**
 * Encodes and decodes JSON Web Tokens.
 * <p>
 * You can construct {@link JwtProcessor} instances by first creating a {@link JwtProcessorBuilder} using the
 * {@link Jwt#processor()} method, then configuring it and calling {@link JwtProcessorBuilder#build()}.
 * <p>
 * JWT payloads are typically {@link Claims} objects when JWTs are used in an authentication/authorization scenario.
 * However, <code>String</code> and {@link java.nio.ByteBuffer ByteBuffer} payloads are supported as well, and you
 * can register any custom payload serializer using the {@link JwtProcessorBuilder#serializePayloadWith} method.
 * <p>
 * All {@link JwtProcessor} instances are immutable and safe for use by concurrent threads.
 */
public interface JwtProcessor {

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
     * Decodes a JSON Web Token and returns its payload.
     * <p>
     * If signature verification was configured via {@link JwtProcessorBuilder#verifyWith} or
     * {@link JwtProcessorBuilder#signAndVerifyWith}, this method also attempts to verify the
     * signature part of the JWT, and throws an exception if the signature is not correct.
     *
     * @param encodedToken the encoded JSON Web Token as a string
     * @param payloadType  the type that the payload should be interpreted as
     * @param <T>          the type of the payload
     * @return the deserialized payload of the token
     * @throws JwtMalformedTokenException       if the input string is not a valid JSON Web Token
     * @throws JwsUnsupportedAlgorithmException if the JWT header specifies an algorithm that is not supported
     *                                          by this JWT processor
     * @throws JwsSignatureException            if the signature could not be verified (this includes
     *                                          {@link org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException JwsSignatureMismatchException}
     *                                          for wrong signatures)
     * @throws IllegalArgumentException         if the payload could not be deserialized into the desired type
     */
    <T> T decode(String encodedToken, Class<T> payloadType) throws JwtMalformedTokenException,
            JwsUnsupportedAlgorithmException, JwsSignatureException;

    /**
     * Decodes a JSON Web Token and returns its payload as a {@link Claims} object.
     * <p>
     * If signature verification was configured via {@link JwtProcessorBuilder#verifyWith} or
     * {@link JwtProcessorBuilder#signAndVerifyWith}, this method also attempts to verify the
     * signature part of the JWT, and throws an exception if the signature is not correct.
     *
     * @param encodedToken the encoded JSON Web Token as a string
     * @return the deserialized {@link Claims} payload of the token
     * @throws JwtMalformedTokenException       if the input string is not a valid JSON Web Token
     * @throws JwsUnsupportedAlgorithmException if the JWT header specifies an algorithm that is not supported
     *                                          by this JWT processor
     * @throws JwsSignatureException            if the signature could not be verified (this includes
     *                                          {@link org.unbrokendome.jsonwebtoken.signature.JwsSignatureMismatchException JwsSignatureMismatchException}
     *                                          for wrong signatures)
     * @throws IllegalArgumentException         if the payload could not be deserialized into a {@link Claims} object
     */
    default Claims decodeClaims(String encodedToken) throws JwtMalformedTokenException,
            JwsUnsupportedAlgorithmException, JwsSignatureException {
        return decode(encodedToken, DefaultClaims.class);
    }
}
