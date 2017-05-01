package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.signature.impl.DefaultMacSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.DefaultAsymmetricSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.NoneSignatureAlgorithm;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * Defines built-in signature algorithms for use with JSON Web Tokens.
 */
public final class SignatureAlgorithms {

    public static final SignatureAlgorithm<Key, Key> NONE = NoneSignatureAlgorithm.getInstance();

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS256 =
            new DefaultMacSignatureAlgorithm("HS256", "HmacSHA256");

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS384 =
            new DefaultMacSignatureAlgorithm("HS384", "HmacSHA384");

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS512 =
            new DefaultMacSignatureAlgorithm("HS512", "HmacSHA512");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS256 =
            new DefaultAsymmetricSignatureAlgorithm("RS256", "SHA256withRSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS384 =
            new DefaultAsymmetricSignatureAlgorithm("RS384", "SHA384withRSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS512 =
            new DefaultAsymmetricSignatureAlgorithm("RS512", "SHA512withRSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES256 =
            new DefaultAsymmetricSignatureAlgorithm("ES256", "SHA256withECDSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES384 =
            new DefaultAsymmetricSignatureAlgorithm("ES384", "SHA384withECDSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> ES512 =
            new DefaultAsymmetricSignatureAlgorithm("ES512", "SHA512withECDSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS256 =
            new DefaultAsymmetricSignatureAlgorithm("PS256", "SHA256WITHRSAANDMGF1");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS384 =
            new DefaultAsymmetricSignatureAlgorithm("PS384", "SHA384WITHRSAANDMGF1");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> PS512 =
            new DefaultAsymmetricSignatureAlgorithm("PS512", "SHA512WITHRSAANDMGF1");


    private SignatureAlgorithms() {
        // Private constructor prevents instantiation
    }
}
