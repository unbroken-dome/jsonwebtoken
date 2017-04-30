package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.JwtMalformedTokenException;
import org.unbrokendome.jsonwebtoken.impl.DefaultClaims;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException;


/**
 * Decodes JSON Web Tokens.
 *
 * <p>
 * You can construct {@link JwtDecodingProcessor} instances by first creating a {@link JwtProcessorBuilder} using the
 * {@link Jwt#processor()} and {@link JwtProcessorBuilder#decodeOnly() decodeOnly()} methods, then configuring it and
 * calling {@link JwtDecodeOnlyProcessorBuilder#build() build()}:
 *
 * <pre>
 * JwtDecodingProcessor processor =
 *     Jwt.processor().decodeOnly()
 *         // ... call methods on the builder ...
 *         .build();
 * </pre>
 *
 * <p>
 * JWT payloads are typically {@link Claims} objects when JWTs are used in an authentication/authorization scenario.
 * However, <code>String</code> and {@link java.nio.ByteBuffer ByteBuffer} payloads are supported as well, and you
 * can register any custom payload deserializer using the {@link JwtProcessorBuilder#deserializePayloadWith} method.
 *
 * <p>
 * All {@link JwtDecodingProcessor} instances are immutable and safe for use by concurrent threads.
 */
public interface JwtDecodingProcessor extends JwtProcessorBase {

    /**
     * Decodes a JSON Web Token and returns its payload.
     *
     * <p>
     * If signature verification was configured via {@link JwtDecodingProcessorBuilderBase#verifyWith verifyWith(...)}
     * or {@link JwtProcessorBuilder#signAndVerifyWith signAndVerifyWith(...)}, this method also attempts to verify the
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
     *
     * <p>
     * If signature verification was configured via {@link JwtDecodingProcessorBuilderBase#verifyWith verifyWith(...)}
     * or {@link JwtProcessorBuilder#signAndVerifyWith signAndVerifyWith(...)}, this method also attempts to verify the
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
