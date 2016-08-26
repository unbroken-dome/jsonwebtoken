package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;


public final class MacPoolableObject extends AbstractAlgorithmPoolableObject<Mac> {

    public MacPoolableObject(String algorithm) {
        super(algorithm);
    }


    @Override
    protected Mac getInstance(String algorithm) throws NoSuchAlgorithmException {
        return Mac.getInstance(algorithm);
    }
}
