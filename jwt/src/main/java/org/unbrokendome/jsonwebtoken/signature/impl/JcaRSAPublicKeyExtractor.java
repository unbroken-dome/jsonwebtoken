package org.unbrokendome.jsonwebtoken.signature.impl;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;


public class JcaRSAPublicKeyExtractor implements PublicKeyExtractor {

    @Override
    public boolean supports(PrivateKey privateKey) {
        return privateKey instanceof RSAPrivateCrtKey;
    }


    @Override
    public PublicKey publicKeyFromPrivateKey(PrivateKey privateKey, KeyFactory keyFactory)
            throws InvalidKeySpecException {
        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent());
        return keyFactory.generatePublic(publicKeySpec);
    }
}
