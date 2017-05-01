package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;

import java.security.Key;


public interface JwtEncodingProcessorBuilderBase<T extends JwtEncodingProcessor, B extends JwtEncodingProcessorBuilderBase<T, B>>
        extends JwtProcessorBuilderBase<T, B> {

    /**
     * Configures the JWT processor to sign every token using the specified algorithm.
     * <p>
     * Only one signing algorithm may be configured; any previously configured signing algorithm will be
     * replaced.
     * <p>
     * This method allows to select a signing key for each token by registering a {@link SigningKeyResolver}.
     * If the same key should be used for each token, you can use the convenience method
     * {@link #signWith(SignatureAlgorithm, Key)} instead.
     *
     * @param algorithm          the signature algorithm to use; typically one of the predefined algorithms from
     *                           {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param signingKeyResolver a function that will select a key for signing a token, based on its payload
     * @param <TSigningKey>      the type of the signing key; must be a subclass of {@link Key}
     * @return the current builder instance
     * @see org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
     */
    <TSigningKey extends Key>
    B signWith(SignatureAlgorithm<TSigningKey, ?> algorithm, SigningKeyResolver<TSigningKey> signingKeyResolver);

    /**
     * Configures the JWT processor to sign every token using the specified algorithm.
     * <p>
     * Only one signing algorithm may be configured; any previously configured signing algorithm will be
     * replaced.
     *
     * @param algorithm     the signature algorithm to use; typically one of the predefined algorithms from
     *                      {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param signingKey    the key to use for signing the tokens
     * @param <TSigningKey> the type of the signing key; must be a subclass of {@link Key}
     * @return the current builder instance
     * @see org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
     */
    default <TSigningKey extends Key>
    B signWith(SignatureAlgorithm<TSigningKey, ?> algorithm, TSigningKey signingKey) {
        return signWith(algorithm, (SigningKeyResolver<TSigningKey>) (h, p) -> signingKey);
    }

    /**
     * Register a strategy for serializing payloads.
     * <p>
     * Per default, the constructed <code>JwtProcessor</code> supports serialization of strings,
     * {@link java.nio.ByteBuffer ByteBuffer}s as well as arbitrary objects to JSON. In addition, custom
     * payload formats can be registered by adding a custom <code>PayloadSerializer</code> using this method.
     *
     * @param payloadSerializer the custom {@link PayloadSerializer} to register
     * @return the current builder instance
     */
    B serializePayloadWith(PayloadSerializer payloadSerializer);

}
