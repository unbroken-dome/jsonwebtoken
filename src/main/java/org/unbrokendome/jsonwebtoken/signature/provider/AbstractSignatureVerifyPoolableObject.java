package org.unbrokendome.jsonwebtoken.signature.provider;

import nf.fr.eraasoft.pool.PoolException;

import javax.annotation.Nullable;
import java.security.Signature;
import java.security.SignatureException;


public abstract class AbstractSignatureVerifyPoolableObject extends AbstractAlgorithmPoolableObject<Signature> {

    public AbstractSignatureVerifyPoolableObject(String algorithm, @Nullable String provider) {
        super(algorithm, provider);
    }


    @Override
    public void activate(Signature signature) throws PoolException {
    }


    @Override
    public void passivate(Signature signature) {
        try {
            signature.verify(null);
        } catch (SignatureException e) {
        }
    }
}
