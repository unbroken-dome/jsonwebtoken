package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;


public class MacAlgorithmProvider implements AlgorithmProvider<Mac> {

    private final String algorithm;


    public MacAlgorithmProvider(String algorithm) {
        this.algorithm = algorithm;
    }


    @Override
    public Mac getInstance() throws NoSuchAlgorithmException {
        return Mac.getInstance(algorithm);
    }


    @Override
    public void returnInstance(Mac instance) {
    }
}
