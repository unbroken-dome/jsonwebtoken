package org.unbrokendome.jsonwebtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadSerializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * Provides a fluent API for configuring and constructing {@link JwtProcessor} instances.
 */
public interface JwtProcessorBuilder {

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
    JwtProcessorBuilder signWith(SignatureAlgorithm<TSigningKey, ?> algorithm,
                                 SigningKeyResolver<TSigningKey> signingKeyResolver);

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
    JwtProcessorBuilder signWith(SignatureAlgorithm<TSigningKey, ?> algorithm,
                                 TSigningKey signingKey) {
        return signWith(algorithm, (SigningKeyResolver<TSigningKey>) (h, p) -> signingKey);
    }

    /**
     * Configures the JWT processor to verify the signature of every token passed to the {@link JwtProcessor#decode}
     * method, using the specified algorithm.
     * <p>
     * <code>verifyWith</code> may be called multiple times with different <em>algorithm</em> arguments, so that
     * the JWT processor will support multiple algorithms.
     * <p>
     * This method allows to select a verification key for each token by registering a {@link VerificationKeyResolver}.
     * If the same key should be used for each token, you can use the convenience method
     * {@link #verifyWith(SignatureAlgorithm, Key)} instead.
     *
     * @param algorithm               the signature algorithm to use; typically one of the predefined algorithms from
     *                                {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param verificationKeyResolver a function that will select a key for verifying a token, based on its header
     *                                and payload
     * @param <TVerificationKey>      the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   VerificationKeyResolver<TVerificationKey> verificationKeyResolver);

    /**
     * Configures the JWT processor to verify the signature of every token passed to the {@link JwtProcessor#decode}
     * method, using the specified algorithm.
     *
     * @param algorithm          the signature algorithm to use; typically one of the predefined algorithms from
     *                           {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param verificationKey    the key to use for verifying the tokens
     * @param <TVerificationKey> the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    default <TVerificationKey extends Key>
    JwtProcessorBuilder verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                                   TVerificationKey verificationKey) {
        return verifyWith(algorithm, (VerificationKeyResolver<TVerificationKey>) ((h, p) -> verificationKey));
    }

    /**
     * Configures the JWT processor to sign every token it creates, and verify the signature of each token passed to
     * the {@link JwtProcessor#decode} method, using the specified algorithm.
     * <p>
     * This method is a convenience shorthand for calling both {@link #signWith} and {@link #verifyWith} with the
     * same algorithm.
     * <p>
     * This method allows to select a signing key and verification key for each token by registering a
     * {@link SigningKeyResolver} and {@link VerificationKeyResolver}. If the same key should be used for each token,
     * you can use the more convenient method {@link #signAndVerifyWith(SignatureAlgorithm, Key, Key)} instead.
     *
     * @param algorithm               the signature algorithm to use; typically one of the predefined algorithms from
     *                                {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param signingKeyResolver      a function that will select a key for signing a token, based on its payload
     * @param verificationKeyResolver a function that will select a key for verifying a token, based on its header
     *                                and payload
     * @param <TSigningKey>           the type of the signing key; must be a subclass of {@link Key}
     * @param <TVerificationKey>      the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    default <TSigningKey extends Key, TVerificationKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TSigningKey, TVerificationKey> algorithm,
                                          SigningKeyResolver<TSigningKey> signingKeyResolver,
                                          VerificationKeyResolver<TVerificationKey> verificationKeyResolver) {
        return signWith(algorithm, signingKeyResolver)
                .verifyWith(algorithm, verificationKeyResolver);
    }

    /**
     * Configures the JWT processor to sign every token it creates, and verify the signature of each token passed to
     * the {@link JwtProcessor#decode} method, using the specified algorithm.
     * <p>
     * This method is a convenience shorthand for calling both {@link #signWith} and {@link #verifyWith} with the
     * same algorithm.
     *
     * @param algorithm          the signature algorithm to use; typically one of the predefined algorithms from
     *                           {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param signingKey         the key to use for signing the tokens
     * @param verificationKey    the key to use for verifying the tokens
     * @param <TSigningKey>      the type of the signing key; must be a subclass of {@link Key}
     * @param <TVerificationKey> the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    default <TSigningKey extends Key, TVerificationKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TSigningKey, TVerificationKey> algorithm,
                                          TSigningKey signingKey, TVerificationKey verificationKey) {
        return signWith(algorithm, signingKey)
                .verifyWith(algorithm, verificationKey);
    }

    /**
     * Configures the JWT processor to sign every token it creates, and verify the signature of each token passed to
     * the {@link JwtProcessor#decode} method, using the specified algorithm.
     * <p>
     * This method is a convenience shorthand for calling both {@link #signWith} and {@link #verifyWith} with the
     * same algorithm and key. It may only be used if the algorithm uses the same key for signing and verification
     * (e.g. any HMAC scheme, like {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms#HS256}).
     *
     * @param algorithm the signature algorithm to use; typically one of the predefined algorithms from
     *                  {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param key       the cryptographic key that will be used for both signing and verification
     * @param <TKey>    the type of the signing/verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    default <TKey extends Key>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TKey, TKey> algorithm, TKey key) {
        return signAndVerifyWith(algorithm, key, key);
    }

    /**
     * Configures the JWT processor to sign every token it creates, and verify the signature of each token passed to
     * the {@link JwtProcessor#decode} method, using the specified algorithm.
     * <p>
     * This method is a convenience shorthand for calling both {@link #signWith} and {@link #verifyWith} with the
     * same algorithm, when the signing/verification keys are available in form of a {@link KeyPair}.
     *
     * @param algorithm the signature algorithm to use; typically one of the predefined algorithms from
     *                  {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param keyPair the cryptographic key pair that will be used for signing and verification
     * @param <TSigningKey> the type of the signing key; must be a subclass of {@link PrivateKey}
     * @param <TVerificationKey> the type of the verification key; must be a subclass of {@link PublicKey}
     * @return the current builder instance
     */
    @SuppressWarnings("unchecked")
    default <TSigningKey extends PrivateKey, TVerificationKey extends PublicKey>
    JwtProcessorBuilder signAndVerifyWith(SignatureAlgorithm<TSigningKey, TVerificationKey> algorithm,
                                          KeyPair keyPair) {
        return signAndVerifyWith(algorithm,
                (SigningKeyResolver<TSigningKey>) (h, p) -> (TSigningKey) keyPair.getPrivate(),
                (h, p) -> (TVerificationKey) keyPair.getPublic());
    }

    /**
     * Register a strategy for serializing and deserializing payloads.
     * <p>
     * Per default, the constructed <code>JwtProcessor</code> supports serialization of strings,
     * {@link java.nio.ByteBuffer ByteBuffer}s as well as arbitrary objects from JSON. In addition, custom
     * payload formats can be registered by adding a custom <code>PayloadSerializer</code> using this method.
     *
     * @param payloadSerializer the custom {@link PayloadSerializer} to register
     * @return the current builder instance
     */
    JwtProcessorBuilder serializePayloadWith(PayloadSerializer<?> payloadSerializer);


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
    JwtProcessorBuilder configurePool(int minSize, int maxIdle);


    /**
     * Configures a custom Jackson {@link ObjectMapper} to use for JSON serialization and deserialization.
     *
     * @param objectMapper the {@link ObjectMapper} instance to use
     * @return the current builder instance
     */
    JwtProcessorBuilder setObjectMapper(ObjectMapper objectMapper);


    /**
     * Builds a {@link JwtProcessor} instance from the current configuration.
     *
     * @return the {@link JwtProcessor}
     */
    JwtProcessor build();
}
