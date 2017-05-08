package org.unbrokendome.jsonwebtoken.signature.impl;

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


public class BouncyCastleECPublicKeyExtractor implements PublicKeyExtractor {

    @Override
    public boolean supports(PrivateKey privateKey) {
        return privateKey instanceof ECPrivateKey;
    }


    @Override
    public PublicKey publicKeyFromPrivateKey(PrivateKey privateKey, KeyFactory keyFactory)
            throws InvalidKeySpecException {

        ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;

        ECParameterSpec ecParams = ecPrivateKey.getParameters();
        ECPoint q = ecParams.getG().multiply(ecPrivateKey.getD());

        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(q, ecParams);

        return keyFactory.generatePublic(publicKeySpec);
    }
}
