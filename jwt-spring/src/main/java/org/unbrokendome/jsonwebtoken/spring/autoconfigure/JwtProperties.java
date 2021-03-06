package org.unbrokendome.jsonwebtoken.spring.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.unbrokendome.jsonwebtoken.IOSupplier;
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessorBuilderBase;
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessorBuilderBase;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilderBase;
import org.unbrokendome.jsonwebtoken.signature.KeyLoader;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;
import org.unbrokendome.jsonwebtoken.signature.SigningKeyResolver;
import org.unbrokendome.jsonwebtoken.signature.VerificationKeyResolver;
import org.unbrokendome.jsonwebtoken.spring.JwtProcessorMode;
import org.unbrokendome.jsonwebtoken.spring.io.Base64DecodingByteSource;
import org.unbrokendome.jsonwebtoken.spring.io.ResourceBinaryDataSupplier;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(prefix = "jwt")
@SuppressWarnings("unused")
public class JwtProperties {

    /**
     * Mode of the processor. Default is {@code FULL}. Set to {@link JwtProcessorMode#ENCODE_ONLY ENCODE_ONLY} or
     * {@link JwtProcessorMode#DECODE_ONLY DECODE_ONLY} if only encoding or decoding capabilities are needed.
     */
    private JwtProcessorMode mode = JwtProcessorMode.FULL;
    private final Pool pool = new Pool();
    private Signing signing;
    private List<Verification> verification = new ArrayList<>(3);

    /**
     * If {@code true}, use the signing algorithm for verification, in addition to any verification strategies
     * given in the {@link #verification} list.
     *
     * Only relevant if the processor {@link #mode} is {@code FULL}.
     */
    private boolean verifyWithSigningAlgorithm = true;


    public JwtProcessorMode getMode() {
        return mode;
    }


    public void setMode(JwtProcessorMode mode) {
        this.mode = mode;
    }


    public Pool getPool() {
        return pool;
    }


    public Signing getSigning() {
        if (signing == null) {
            signing = new Signing();
        }
        return signing;
    }


    public List<Verification> getVerification() {
        return verification;
    }


    public boolean isVerifyWithSigningAlgorithm() {
        return verifyWithSigningAlgorithm;
    }


    public void setVerifyWithSigningAlgorithm(boolean verifyWithSigningAlgorithm) {
        this.verifyWithSigningAlgorithm = verifyWithSigningAlgorithm;
    }


    public static class Pool {

        /**
         * Enable pooling of crypto instances for signing or verification.
         */
        private boolean enabled = true;

        /**
         * Minimum size of the crypto pool.
         */
        private int minSize = 10;

        /**
         * Maximum number of idle objects. If more than this number become idle, instances will be released from
         * the pool.
         */
        private int maxIdle = 10;


        public boolean isEnabled() {
            return enabled;
        }


        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }


        public int getMinSize() {
            return minSize;
        }


        public void setMinSize(int minSize) {
            this.minSize = minSize;
        }


        public int getMaxIdle() {
            return maxIdle;
        }


        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }


        void configure(JwtProcessorBuilderBase<?, ?> builder) {
            if (enabled) {
                builder.configurePool(minSize, maxIdle);
            }
        }
    }


    public static abstract class SigningOrVerification {

        /**
         * The name of the algorithm. This should be one of the known JWA names, e.g. {@code ES256} or {@code HS512}.
         */
        private String algorithmName;

        /**
         * The Java Security provider to use for cryptographic operations. Note that this does not install the
         * provider.
         */
        private String provider;

        /**
         * A resource that contains the key material. Either this property or {@link #key} should be specified,
         * but not both.
         */
        private Resource keyResource;

        /**
         * A Base64-encoded representation of the key material. Either this property or {@link #keyResource} should
         * be specified, but not both.
         */
        private String key;


        public String getAlgorithm() {
            return algorithmName;
        }


        public void setAlgorithm(String algorithmName) {
            this.algorithmName = algorithmName;
        }


        public String getProvider() {
            return provider;
        }


        public void setProvider(String provider) {
            this.provider = provider;
        }


        public Resource getKeyResource() {
            return keyResource;
        }


        public void setKeyResource(Resource keyResource) {
            this.keyResource = keyResource;
        }


        public String getKey() {
            return key;
        }


        public void setKey(String key) {
            this.key = key;
        }


        @Nullable
        SignatureAlgorithm<?, ?> getAlgorithmObject() {
            if (StringUtils.isEmpty(algorithmName)) {
                return null;
            }

            if (!StringUtils.isEmpty(provider)) {
                try {
                    return SignatureAlgorithms.get(algorithmName, provider);
                } catch (NoSuchProviderException ex) {
                    throw new IllegalArgumentException(ex);
                }
            } else {
                return SignatureAlgorithms.get(algorithmName);
            }
        }


        @Nullable
        IOSupplier<byte[]> getKeyByteSource() {
            if (key != null && !key.trim().isEmpty()) {
                return new Base64DecodingByteSource(() -> key, StandardCharsets.UTF_8);

            } else if (keyResource != null) {
                return new Base64DecodingByteSource(
                        new ResourceBinaryDataSupplier(keyResource));

            } else {
                return null;
            }
        }


        @Nullable
        Key loadSigningKey(SignatureAlgorithm<?, ?> algorithm) {

            IOSupplier<byte[]> keyByteSource = getKeyByteSource();
            if (keyByteSource == null) {
                if (algorithm != SignatureAlgorithms.NONE) {
                    throw new IllegalStateException("A signature algorithm other than NONE was specified, but no signing " +
                            "key was given. Please configure either jwt.signing.key or jwt.signing.keyResource.");
                } else {
                    return null;
                }
            }

            KeyLoader<?> signingKeyLoader = algorithm.getSigningKeyLoader();
            if (signingKeyLoader == null) {
                throw new IllegalStateException("Algorithm " + algorithm + " cannot be used for signing "
                        + "because it does not specify how to load its signing key. When using a custom "
                        + "SignatureAlgorithm, please make sure getSigningKeyLoader() returns an instance.");
            }

            try {
                return signingKeyLoader.load(keyByteSource);

            } catch (Exception ex) {
                throw new IllegalStateException("Error while loading signing key", ex);
            }
        }


        protected abstract KeyLoader<?> getVerificationKeyLoader(SignatureAlgorithm<?, ?> algorithm);


        @Nullable
        Key loadVerificationKey(SignatureAlgorithm<?, ?> algorithm) {

            IOSupplier<byte[]> keyByteSource = getKeyByteSource();
            if (keyByteSource == null) {
                if (algorithm != SignatureAlgorithms.NONE) {
                    throw new IllegalStateException("A signature algorithm other than NONE was specified, but no verification " +
                            "key was given. Please configure either jwt.verification.key or jwt.verification.keyResource.");
                } else {
                    return null;
                }
            }

            KeyLoader<?> verificationKeyLoader = getVerificationKeyLoader(algorithm);
            if (verificationKeyLoader == null) {
                throw new IllegalStateException("Algorithm " + algorithm + " cannot be used for verification "
                        + "because it does not specify how to load its verification key. When using a custom "
                        + "SignatureAlgorithm, please make sure getVerificationKeyLoader() returns an instance.");
            }

            try {
                return verificationKeyLoader.load(keyByteSource);

            } catch (Exception ex) {
                throw new IllegalStateException("Error while loading verification key", ex);
            }
        }


        @SuppressWarnings({ "rawtypes", "unchecked" })
        void configure(JwtDecodingProcessorBuilderBase<?, ?> builder) {
            SignatureAlgorithm<?, ?> algorithm = getAlgorithmObject();
            if (algorithm != null) {
                Key verificationKey = loadVerificationKey(algorithm);
                VerificationKeyResolver keyResolver = (header, payload) -> verificationKey;
                builder.verifyWith(algorithm, keyResolver);
            }
        }
    }


    public static class Signing extends SigningOrVerification {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        void configure(JwtEncodingProcessorBuilderBase<?, ?> builder) {
            SignatureAlgorithm<?, ?> algorithm = getAlgorithmObject();
            if (algorithm != null) {
                Key signingKey = loadSigningKey(algorithm);
                SigningKeyResolver keyResolver = (header, payload) -> signingKey;
                builder.signWith(algorithm, keyResolver);
            }
        }


        @Override
        protected KeyLoader<?> getVerificationKeyLoader(SignatureAlgorithm<?, ?> algorithm) {
            return algorithm.getVerificationKeyLoader(true);
        }
    }


    public static class Verification extends SigningOrVerification {

        @Override
        protected KeyLoader<?> getVerificationKeyLoader(SignatureAlgorithm<?, ?> algorithm) {
            return algorithm.getVerificationKeyLoader(false);
        }
    }


    void configure(JwtProcessorBuilder builder) {
        configureCommon(builder);
        configureEncoding(builder);
        configureDecoding(builder);
    }


    void configure(JwtEncodeOnlyProcessorBuilder builder) {
        configureCommon(builder);
        configureEncoding(builder);
    }


    void configure(JwtDecodeOnlyProcessorBuilder builder) {
        configureCommon(builder);
        configureDecoding(builder);
    }


    private void configureCommon(JwtProcessorBuilderBase<?, ?> builder) {
        pool.configure(builder);
    }


    private void configureEncoding(JwtEncodingProcessorBuilderBase<?, ?> builder) {
        if (signing != null) {
            signing.configure(builder);
        }
    }


    private void configureDecoding(JwtDecodingProcessorBuilderBase<?, ?> builder) {
        if (signing != null && verifyWithSigningAlgorithm) {
            signing.configure(builder);
        }
        if (verification != null) {
            verification.forEach(v -> v.configure(builder));
        }
    }
}
