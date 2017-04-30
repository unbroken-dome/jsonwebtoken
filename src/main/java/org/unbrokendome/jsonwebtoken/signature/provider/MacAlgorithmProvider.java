package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public final class MacAlgorithmProvider implements AlgorithmProvider<Mac> {

    private final String algorithm;
    private final String provider;


    public MacAlgorithmProvider(String algorithm, @Nullable String provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }


    @Override
    public Mac getInstance() throws NoSuchAlgorithmException, NoSuchProviderException {
        if (provider != null) {
            return Mac.getInstance(algorithm, provider);
        } else {
            return Mac.getInstance(algorithm);
        }
    }


    @Override
    public void returnInstance(Mac instance) {
    }
}
