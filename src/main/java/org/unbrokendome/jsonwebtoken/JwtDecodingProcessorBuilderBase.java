package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.encoding.payload.PayloadDeserializer;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;

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
     *                                {@link org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms SignatureAlgorithms}
     * @param verificationKeyResolver a function that will select a key for verifying a token, based on its header
     *                                and payload
     * @param <TVerificationKey>      the type of the verification key; must be a subclass of {@link Key}
     * @return the current builder instance
     */
    <TVerificationKey extends Key>
    B verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm,
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
    B verifyWith(SignatureAlgorithm<?, TVerificationKey> algorithm, TVerificationKey verificationKey) {
        return verifyWith(algorithm, (VerificationKeyResolver<TVerificationKey>) ((h, p) -> verificationKey));
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
    B deserializePayloadWith(PayloadDeserializer<?> payloadDeserializer);

}
