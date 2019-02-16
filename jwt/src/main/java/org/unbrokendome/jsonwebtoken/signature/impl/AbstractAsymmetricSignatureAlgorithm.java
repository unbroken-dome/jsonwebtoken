package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.AsymmetricSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;
import org.unbrokendome.jsonwebtoken.signature.Signer;
import org.unbrokendome.jsonwebtoken.signature.Verifier;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;
import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProviders;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;
import org.unbrokendome.jsonwebtoken.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


abstract class AbstractAsymmetricSignatureAlgorithm
        extends AbstractSignatureAlgorithm<PrivateKey, PublicKey>
        implements AsymmetricSignatureAlgorithm<PrivateKey, PublicKey> {

    private final String keyFamily;


    AbstractAsymmetricSignatureAlgorithm(String jwaName, String jcaName,
                                         String keyFamily, @Nullable String jcaProvider) {
        super(jwaName, jcaName, jcaProvider);
        this.keyFamily = keyFamily;

        try {
            // Instantiate the KeyFactory once so we can fail fast if the provider does not support this key family
            if (jcaProvider != null) {
                KeyFactory.getInstance(keyFamily, jcaProvider);
            } else {
                KeyFactory.getInstance(keyFamily);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("No installed provider supports KeyFactory " + keyFamily, ex);
        } catch (NoSuchProviderException ex) {
            throw new IllegalArgumentException("Unknown provider: " + jcaProvider, ex);
        }
    }


    AbstractAsymmetricSignatureAlgorithm(String jwaName, String jcaName,
                                         String keyFamily) {
        this(jwaName, jcaName, keyFamily, null);
    }


    @Nonnull
    @Override
    public Signer<PrivateKey> createSigner(@Nullable PoolConfigurer poolConfigurer) {
        return new SignaturePrivateKeySigner(
                getAlgorithmProvider(poolConfigurer));
    }


    @Nonnull
    @Override
    public Verifier<PublicKey> createVerifier(@Nullable PoolConfigurer poolConfigurer) {
        return new SignaturePublicKeyVerifier(
                getAlgorithmProvider(poolConfigurer));
    }


    @Nonnull
    @Override
    public Pair<Signer<PrivateKey>, Verifier<PublicKey>>
    createSignerAndVerifier(@Nullable PoolConfigurer poolConfigurer) {

        AlgorithmProvider<Signature> provider = getAlgorithmProvider(poolConfigurer);

        Signer<PrivateKey> signer = new SignaturePrivateKeySigner(provider);
        Verifier<PublicKey> verifier = new SignaturePublicKeyVerifier(provider);

        return Pair.of(signer, verifier);
    }


    @Nonnull
    @Override
    public final KeyFactory getKeyFactory() {
        String jcaProvider = getJcaProvider();
        if (jcaProvider != null) {
            try {
                return KeyFactory.getInstance(keyFamily, jcaProvider);

            } catch (NoSuchProviderException ex) {
                // We checked in the constructor already, so this should not happen unless the provider
                // was removed afterwards
                throw new IllegalStateException("Provider \"" + jcaProvider + "\" is not installed", ex);

            } catch (NoSuchAlgorithmException ex) {
                // We checked in the constructor already, so this should not happen
                throw new IllegalStateException("Provider \"" + jcaProvider + "\" does not support KeyFactory "
                        + keyFamily, ex);
            }
        }

        String preferredProvider = getPreferredProvider();
        if (preferredProvider != null) {
            try {
                return KeyFactory.getInstance(keyFamily, preferredProvider);
            } catch (NoSuchProviderException | NoSuchAlgorithmException ex) {
                // The preferred provider is not installed or does not support this type of key.
                // Skip and continue with any provider
            }
        }

        try {
            return KeyFactory.getInstance(keyFamily);

        } catch (NoSuchAlgorithmException ex) {
            // We checked in the constructor already, so this should not happen
            throw new IllegalArgumentException("No installed provider supports KeyFactory " + keyFamily, ex);
        }
    }


    @Nullable
    String getPreferredProvider() {
        return null;
    }


    @Override
    public KeyLoader<PrivateKey> getSigningKeyLoader() {
        return new PKCS8PrivateKeyLoader(this::getKeyFactory);
    }


    @Override
    public KeyLoader<PublicKey> getVerificationKeyLoader(boolean fromSigningKey) {
        if (fromSigningKey) {
            KeyLoader<PrivateKey> signingKeyLoader = getSigningKeyLoader();
            if (signingKeyLoader == null) {
                return null;
            }
            return new PublicKeyFromPrivateKeyLoader(signingKeyLoader, getPublicKeyExtractor(), getKeyFactory());
        }
        return new X509PublicKeyLoader(this::getKeyFactory);
    }


    @Nonnull
    protected abstract PublicKeyExtractor getPublicKeyExtractor();


    private AlgorithmProvider<Signature> getAlgorithmProvider(@Nullable PoolConfigurer poolConfigurer) {
        return AlgorithmProviders.signature(getJcaName(), getJcaProvider(), poolConfigurer);
    }
}
