package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

import javax.annotation.Nonnull;
import java.security.Key;


public interface JwtDecodingProcessorBuilderBase<T extends JwtDecodingProcessor, B extends JwtDecodingProcessorBuilderBase<T, B>>
        extends JwtProcessorBuilderBase<T, B> {

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
     *                                {@link SignatureAlgorithms}
     * @param verificationKeyResolver a function that will select a key for verifying a token, based on its header
     *                                and payload
     * @param <TVerificationKey>      the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    @Nonnull
    <TVerificationKey extends Key>
    B verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
                 VerificationKeyResolver<TVerificationKey> verificationKeyResolver);

    /**
     * Configures the JWT processor to verify the signature of every token passed to the {@link JwtProcessor#decode}
     * method, using the specified algorithm.
     *
     * @param algorithm          the signature algorithm to use; typically one of the predefined algorithms from
     *                           {@link SignatureAlgorithms}
     * @param verificationKey    the key to use for verifying the tokens
     * @param <TVerificationKey> the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    @Nonnull
    default <TVerificationKey extends Key>
    B verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm, TVerificationKey verificationKey) {
        return verifyWith(algorithm, (VerificationKeyResolver<TVerificationKey>) ((h, p) -> verificationKey));
    }

    /**
     * Configures the JWT processor to verify the signature of every token passed to the {@link JwtProcessor#decode}
     * method, using the specified algorithm.
     * <p>
     * This overload does not take a key parameter, and is intended for the {@code NONE} algorithm (which is the only
     * algorithm that does not require a key for verification).
     *
     * @param algorithm the signature algorithm to use; usually {@link SignatureAlgorithms#NONE}
     * @return the current builder instance
     */
    @Nonnull
    default B verifyWith(SignatureAlgorithm<?, NoneKey> algorithm) {
        return verifyWith(algorithm, NoneKey.getInstance());
    }

    /**
     * Register a strategy for deserializing payloads.
     * <p>
     * Per default, the constructed <code>JwtProcessor</code> supports deserialization of strings,
     * {@link java.nio.ByteBuffer ByteBuffer}s as well as arbitrary objects from JSON. In addition, custom
     * payload formats can be registered by adding a custom <code>PayloadDeserializer</code> using this method.
     *
     * @param payloadDeserializer the custom {@link PayloadDeserializer} to register
     * @return the current builder instance
     */
    @Nonnull
    B deserializePayloadWith(PayloadDeserializer<?> payloadDeserializer);
}
