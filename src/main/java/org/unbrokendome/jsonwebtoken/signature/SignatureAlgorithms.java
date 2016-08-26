package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.signature.impl.DefaultMacSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.DefaultRsaSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.NoneSignatureAlgorithm;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;


public final class SignatureAlgorithms {

    public static final SignatureAlgorithm<Key, Key> NONE = NoneSignatureAlgorithm.getInstance();

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS256 =
            new DefaultMacSignatureAlgorithm("HS256", "HmacSHA256");

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS384 =
            new DefaultMacSignatureAlgorithm("HS384", "HmacSHA384");

    public static final SignatureAlgorithm<SecretKey, SecretKey> HS512 =
            new DefaultMacSignatureAlgorithm("HS512", "HmacSHA512");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS256 =
            new DefaultRsaSignatureAlgorithm("RS256", "SHA256withRSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS384 =
            new DefaultRsaSignatureAlgorithm("RS384", "SHA384withRSA");

    public static final SignatureAlgorithm<PrivateKey, PublicKey> RS512 =
            new DefaultRsaSignatureAlgorithm("RS512", "SHA512withRSA");


    private SignatureAlgorithms() {
        // Private constructor prevents instantiation
    }
}
