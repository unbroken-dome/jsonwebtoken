package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;


public final class SignaturePoolableObject extends AbstractAlgorithmPoolableObject<Signature> {

    public SignaturePoolableObject(String algorithm, @Nullable String provider) {
        super(algorithm, provider);
    }


    @Override
    protected Signature getInstance(String algorithm, @Nullable String provider)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        if (provider != null) {
            return Signature.getInstance(algorithm, provider);
        } else {
            return Signature.getInstance(algorithm);
        }
    }
}
