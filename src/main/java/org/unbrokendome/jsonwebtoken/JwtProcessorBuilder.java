package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
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
public interface JwtProcessorBuilder
        extends JwtEncodingProcessorBuilderBase<JwtProcessor, JwtProcessorBuilder>,
                JwtDecodingProcessorBuilderBase<JwtProcessor, JwtProcessorBuilder> {

    /**
     * Returns a builder to create a JWT processor that only encodes (but does not decode) tokens.
     * <p>
     * The returned builder will keep all the relevant settings from the current builder (but not the ones that only
     * apply to token decoding).
     *
     * @return a {@link JwtEncodeOnlyProcessorBuilder}
     */
    JwtEncodeOnlyProcessorBuilder encodeOnly();

    /**
     * Returns a builder to create a JWT processor that only decodes (but does not encode) tokens.
     * <p>
     * The returned builder will keep all the relevant settings from the current builder (but not the ones that only
     * apply to token encoding).
     *
     * @return a {@link JwtDecodeOnlyProcessorBuilder}
     */
    JwtDecodeOnlyProcessorBuilder decodeOnly();

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
     * This is a convenience method for cases where the registered object implements both {@link PayloadSerializer}
     * and {@link PayloadDeserializer}. It is equivalent to calling {@link #serializePayloadWith} and
     * {@link #deserializePayloadWith} with the same argument.
     * <p>
     * Per default, the constructed <code>JwtProcessor</code> supports serialization of strings,
     * {@link java.nio.ByteBuffer ByteBuffer}s as well as arbitrary objects from JSON. In addition, custom
     * payload formats can be registered by adding a custom payload serializer/deserializer using this method.
     *
     * @param payloadSerializer the custom payload serializer/deserializer to register
     * @return the current builder instance
     */
    default <T extends PayloadSerializer & PayloadDeserializer<?>>
    JwtProcessorBuilder serializeAndDeserializePayloadWith(T payloadSerializer) {
        return serializePayloadWith(payloadSerializer)
                .deserializePayloadWith(payloadSerializer);
    }

    /**
     * Builds a {@link JwtProcessor} instance from the current configuration.
     *
     * @return the {@link JwtProcessor}
     */
    @Override
    JwtProcessor build();
}
