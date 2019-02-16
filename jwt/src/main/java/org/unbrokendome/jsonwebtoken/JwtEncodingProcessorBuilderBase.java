package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;

import javax.annotation.Nonnull;
import java.security.Key;
import java.util.function.Consumer;


public interface JwtEncodingProcessorBuilderBase<T extends JwtEncodingProcessor, B extends JwtEncodingProcessorBuilderBase<T, B>>
        extends JwtProcessorBuilderBase<T, B> {

    /**
     * Adds an action that modifies the JOSE header of each encoded JWT.
     * <p>
     * Note that the {@code alg} parameter should not be set by such an action; it will most likely be overridden
     * by the {@link SignatureAlgorithm} when the token is signed.
     *
     * @param headerProcessor a {@link Consumer} that takes a {@link JoseHeaderBuilder}, possibly modifying
     * @return the current builder instance
     */
    @Nonnull
    B header(Consumer<? super JoseHeaderBuilder> headerProcessor);

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
     *                           {@link SignatureAlgorithms SignatureAlgorithms}
     * @param signingKeyResolver a function that will select a key for signing a token, based on its payload
     * @param <TSigningKey>      the type of the signing key; must be a subclass of {@link Key}
     * @return the current builder instance
     * @see SignatureAlgorithms
     */
    @Nonnull
    <TSigningKey extends Key>
    B signWith(SignatureAlgorithm<TSigningKey, ?> algorithm, SigningKeyResolver<TSigningKey> signingKeyResolver);

    /**
     * Configures the JWT processor to sign every token using the specified algorithm.
     * <p>
     * Only one signing algorithm may be configured; any previously configured signing algorithm will be
     * replaced.
     *
     * @param algorithm     the signature algorithm to use; typically one of the predefined algorithms from
     *                      {@link SignatureAlgorithms SignatureAlgorithms}
     * @param signingKey    the key to use for signing the tokens
     * @param <TSigningKey> the type of the signing key; must be a subclass of {@link Key}
     * @return the current builder instance
     * @see SignatureAlgorithms
     */
    @Nonnull
    default <TSigningKey extends Key>
    B signWith(SignatureAlgorithm<TSigningKey, ?> algorithm, TSigningKey signingKey) {
        return signWith(algorithm, (SigningKeyResolver<TSigningKey>) (h, p) -> signingKey);
    }

    /**
     * Configures the JWT processor to sign every token using the specified algorithm.
     * <p>
     * Only one signing algorithm may be configured; any previously configured signing algorithm will be
     * replaced.
     * <p>
     * This overload does not take a key parameter, and is intended for the {@code NONE} algorithm (which is the only
     * algorithm that does not require a key for verification). Note that {@code NONE} is the algorithm used by default
     *
     * @param algorithm the signature algorithm to use; typically {@link SignatureAlgorithms#NONE}
     * @return the current builder instance
     */
    @Nonnull
    default B signWith(SignatureAlgorithm<NoneKey, ?> algorithm) {
        return signWith(algorithm, NoneKey.getInstance());
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
    @Nonnull
    B serializePayloadWith(PayloadSerializer payloadSerializer);
}
