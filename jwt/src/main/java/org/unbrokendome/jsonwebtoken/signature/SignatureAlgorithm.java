package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;
import org.unbrokendome.jsonwebtoken.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.Key;


/**
 * Defines a cryptographic algorithm for signing JSON Web Tokens and verifying their signatures.
 *
 * <p>
 * All built-in algorithms are defined as constant fields in the {@link SignatureAlgorithms} class.
 *
 * @param <TSigningKey>      the type of signing key used by this algorithm
 * @param <TVerificationKey> the type of verification key used by this algorithm
 */
public interface SignatureAlgorithm<TSigningKey extends Key, TVerificationKey extends Key> {

    /**
     * Gets the JSON Web Algorithm (JWA) identifier, as defined in
     * <a href="https://tools.ietf.org/html/rfc7518">RFC 7518</a>. This is also the identifier that will
     * be put in the <code>alg</code> field of the header.
     *
     * @return the JWA name of this algorithm
     */
    @Nonnull
    String getJwaName();

    /**
     * Creates a {@link Signer} that can be used to create signatures.
     *
     * @param poolConfigurer a function that configures the {@link nf.fr.eraasoft.pool.PoolSettings PoolSettings}
     *                       for the verifier; may be <code>null</code> if no pooling should be used
     * @return the {@link Signer} instance
     */
    @Nonnull
    Signer<TSigningKey> createSigner(@Nullable PoolConfigurer poolConfigurer);

    /**
     * Creates a {@link Verifier} that can be used to verify signatures.
     *
     * @param poolConfigurer a function that configures the {@link nf.fr.eraasoft.pool.PoolSettings PoolSettings}
     *                       for the verifier; may be <code>null</code> if no pooling should be used
     * @return the {@link Verifier} instance
     */
    @Nonnull
    Verifier<TVerificationKey> createVerifier(@Nullable PoolConfigurer poolConfigurer);

    /**
     * Creates both a signer and a verifier for this algorithm.
     * <p>
     * The default implementation simply calls both {@link #createSigner} and {@link #createVerifier}, but may be
     * overridden by an implementation if create both signer and verifier at the same time is more efficient, for
     * example because certain objects can be shared.
     *
     * @param poolConfigurer a function that configures the {@link nf.fr.eraasoft.pool.PoolSettings PoolSettings}
     *                       for the signer and verifier; may be <code>null</code> if no pooling should be used
     * @return a {@link Pair} that contains both a {@link Signer} and a {@link Verifier} instance
     */
    @Nonnull
    default Pair<Signer<TSigningKey>, Verifier<TVerificationKey>> createSignerAndVerifier(
            @Nullable PoolConfigurer poolConfigurer) {
        return Pair.of(createSigner(poolConfigurer), createVerifier(poolConfigurer));
    }

    /**
     * Modifies the JOSE header to store this algorithm.
     *
     * @param header the JOSE header under construction, as a {@link JoseHeaderBuilder}
     */
    default void createHeader(JoseHeaderBuilder header) {
        header.setAlgorithm(getJwaName());
    }

    /**
     * Gets the {@link KeyLoader} that can be used to load the signing key from key material.
     *
     * @return the {@link KeyLoader} for the signing key, or {@code null}
     */
    @Nullable
    KeyLoader<TSigningKey> getSigningKeyLoader();

    /**
     * Gets the {@link KeyLoader} that can be used to load the verification key from key material.
     *
     * @param fromSigningKey {@code true} if the verification key should be loaded from the same material as the
     *                       signing key; {@code false} otherwise
     * @return the {@link KeyLoader} for the signing key, or {@code null}
     */
    @Nullable
    KeyLoader<TVerificationKey> getVerificationKeyLoader(boolean fromSigningKey);
}
