package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


final class MacPoolableObject extends AbstractAlgorithmPoolableObject<Mac> {

    MacPoolableObject(String algorithm, @Nullable String provider) {
        super(algorithm, provider);
    }


    @Override
    protected Mac getInstance(String algorithm, @Nullable String provider)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        if (provider != null) {
            return Mac.getInstance(algorithm, provider);
        } else {
            return Mac.getInstance(algorithm);
        }
    }
}
