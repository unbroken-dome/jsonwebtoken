package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import java.security.*;


public class SignatureAlgorithmProvider implements AlgorithmProvider<Signature> {

    private final String algorithm;
    private final String provider;


    public SignatureAlgorithmProvider(String algorithm, @Nullable String provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }


    @Override
    public Signature getInstance() throws NoSuchAlgorithmException, NoSuchProviderException {
        if (provider != null) {
            return Signature.getInstance(algorithm, provider);
        } else {
            return Signature.getInstance(algorithm);
        }
    }


    @Override
    public void returnInstance(Signature instance) {
    }
}
