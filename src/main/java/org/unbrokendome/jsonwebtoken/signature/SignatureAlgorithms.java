package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.signature.impl.DefaultMacSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.DefaultRsaSignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.impl.NoneSignatureAlgorithm;


public final class SignatureAlgorithms {

    public static final SignatureAlgorithm NONE = NoneSignatureAlgorithm.getInstance();
    public static final SignatureAlgorithm HS256 = new DefaultMacSignatureAlgorithm("HS256", "HmacSHA256");
    public static final SignatureAlgorithm HS384 = new DefaultMacSignatureAlgorithm("HS384", "HmacSHA384");
    public static final SignatureAlgorithm HS512 = new DefaultMacSignatureAlgorithm("HS512", "HmacSHA512");
    public static final SignatureAlgorithm RS256 = new DefaultRsaSignatureAlgorithm("RS256", "SHA256withRSA");
    public static final SignatureAlgorithm RS384 = new DefaultRsaSignatureAlgorithm("RS384", "SHA384withRSA");
    public static final SignatureAlgorithm RS512 = new DefaultRsaSignatureAlgorithm("RS512", "SHA512withRSA");


    private SignatureAlgorithms() {
    }
}
