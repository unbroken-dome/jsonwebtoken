package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;


public class RsaAlgorithmProvider implements AlgorithmProvider<Signature> {

    private final String algorithm;


    public RsaAlgorithmProvider(String algorithm) {
        this.algorithm = algorithm;
    }


    @Override
    public Signature getInstance() throws NoSuchAlgorithmException {
        return Signature.getInstance(algorithm);
    }


    @Override
    public void returnInstance(Signature instance) {
    }
}
