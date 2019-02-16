package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.signature.impl.EllipticCurveSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.MacSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.NoneSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.RSASignatureAlgorithm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;


/**
 * Defines built-in signature algorithms for use with JSON Web Tokens.
 */
public final class SignatureAlgorithms {

    public static final SignatureAlgorithm<NoneKey, NoneKey> NONE = getUnchecked(KnownAlgorithm.NONE);
    public static final SignatureAlgorithm<SecretKey, SecretKey> HS256 = getUnchecked(KnownAlgorithm.HS256);
    public static final SignatureAlgorithm<SecretKey, SecretKey> HS384 = getUnchecked(KnownAlgorithm.HS384);
    public static final SignatureAlgorithm<SecretKey, SecretKey> HS512 = getUnchecked(KnownAlgorithm.HS512);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS256 = getUnchecked(KnownAlgorithm.RS256);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS384 = getUnchecked(KnownAlgorithm.RS384);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS512 = getUnchecked(KnownAlgorithm.RS512);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES256 = getUnchecked(KnownAlgorithm.ES256);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES384 = getUnchecked(KnownAlgorithm.ES384);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES512 = getUnchecked(KnownAlgorithm.ES512);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS256 = getUnchecked(KnownAlgorithm.PS256);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS384 = getUnchecked(KnownAlgorithm.PS384);
    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS512 = getUnchecked(KnownAlgorithm.PS512);

    private SignatureAlgorithms() {
        // Private constructor prevents instantiation
    }


    @Nonnull
    public static SignatureAlgorithm<?, ?> get(KnownAlgorithm algorithm, String provider)
            throws NoSuchProviderException {
        if (Security.getProvider(provider) == null) {
            throw new NoSuchProviderException("Cannot find provider: " + provider);
        }
        return algorithm.getAlgorithm(provider);
    }


    @Nonnull
    public static SignatureAlgorithm<?, ?> get(KnownAlgorithm algorithm) {
        return algorithm.getAlgorithm();
    }


    @Nonnull
    public static SignatureAlgorithm<?, ?> get(String jwaName, String provider)
            throws NoSuchProviderException {
        if (Security.getProvider(provider) == null) {
            throw new NoSuchProviderException("Cannot find provider: " + provider);
        }
        return KnownAlgorithm.valueOf(jwaName).getAlgorithm(provider);
    }


    @Nonnull
    public static SignatureAlgorithm<?, ?> get(String jwaName) {
        return KnownAlgorithm.valueOf(jwaName).getAlgorithm();
    }


    @SuppressWarnings("unchecked")
    @Nonnull
    private static <T extends SignatureAlgorithm<?, ?>>
    T getUnchecked(KnownAlgorithm algorithm) {
        return (T) algorithm.getAlgorithm();
    }


    public enum KnownAlgorithm {
        NONE {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return NoneSignatureAlgorithm.getInstance();
            }
        },
        HS256 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new MacSignatureAlgorithm("HS256", "HmacSHA256", provider);
            }
        },
        HS384 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new MacSignatureAlgorithm("HS384", "HmacSHA384", provider);
            }
        },
        HS512 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new MacSignatureAlgorithm("HS512", "HmacSHA512", provider);
            }
        },
        RS256 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("RS256", "SHA256withRSA",
                        "RSA", provider);
            }
        },
        RS384 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("RS384", "SHA384withRSA",
                        "RSA", provider);
            }
        },
        RS512 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("RS512", "SHA512withRSA",
                        "RSA", provider);
            }
        },
        ES256 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new EllipticCurveSignatureAlgorithm("ES256", "SHA256withECDSA", provider);
            }
        },
        ES384 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new EllipticCurveSignatureAlgorithm("ES384", "SHA384withECDSA", provider);
            }
        },
        ES512 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new EllipticCurveSignatureAlgorithm("ES512", "SHA512withECDSA", provider);
            }
        },
        PS256 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("PS256", "SHA256WITHRSAANDMGF1",
                        "RSA", provider);
            }
        },
        PS384 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("PS384", "SHA384WITHRSAANDMGF1",
                        "RSA", provider);
            }
        },
        PS512 {
            @Nonnull
            @Override
            SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider) {
                return new RSASignatureAlgorithm("PS512", "SHA512WITHRSAANDMGF1",
                        "RSA", provider);
            }
        };


        @Nonnull
        abstract SignatureAlgorithm<?, ?> getAlgorithm(@Nullable String provider);


        @Nonnull
        final SignatureAlgorithm<?, ?> getAlgorithm() {
            return getAlgorithm(null);
        }
    }
}
