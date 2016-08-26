package org.unbrokendome.jsonwebtoken.signature.provider;

import nf.fr.eraasoft.pool.PoolException;
import nf.fr.eraasoft.pool.PoolableObjectBase;

import java.security.GeneralSecurityException;


public abstract class AbstractAlgorithmPoolableObject<T> extends PoolableObjectBase<T> {

    private final String algorithm;


    public AbstractAlgorithmPoolableObject(String algorithm) {
        this.algorithm = algorithm;
    }


    @Override
    public T make() throws PoolException {
        try {
            return getInstance(algorithm);
        } catch (GeneralSecurityException e) {
            throw new PoolException(e);
        }
    }


    @Override
    public void activate(T t) throws PoolException {
    }


    protected abstract T getInstance(String algorithm) throws GeneralSecurityException;
}
