package org.unbrokendome.jsonwebtoken.signature.impl;

import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;


public class BouncyCastleRsaPublicKeyExtractor implements PublicKeyExtractor {

    @Override
    public boolean supports(PrivateKey privateKey) {
        return privateKey instanceof BCRSAPrivateCrtKey;
    }


    @Override
    public PublicKey publicKeyFromPrivateKey(PrivateKey privateKey, KeyFactory keyFactory)
            throws InvalidKeySpecException {
        BCRSAPrivateCrtKey privateCrtKey = (BCRSAPrivateCrtKey) privateKey;
        RSAPublicKeySpec publicKeySpec =
                new RSAPublicKeySpec(privateCrtKey.getModulus(), privateCrtKey.getPublicExponent());
        return keyFactory.generatePublic(publicKeySpec);
    }
}
