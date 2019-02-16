package org.unbrokendome.jsonwebtoken.signature.provider;

import nf.fr.eraasoft.pool.PoolException;
import nf.fr.eraasoft.pool.PoolableObjectBase;

import javax.annotation.Nullable;
import java.security.GeneralSecurityException;


abstract class AbstractAlgorithmPoolableObject<T> extends PoolableObjectBase<T> {

    private final String algorithm;
    private final String provider;


    AbstractAlgorithmPoolableObject(String algorithm, @Nullable String provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }


    @Override
    public T make() throws PoolException {
        try {
            return getInstance(algorithm, provider);
        } catch (GeneralSecurityException e) {
            throw new PoolException(e);
        }
    }


    @Override
    public void activate(T t) {
    }


    protected abstract T getInstance(String algorithm, @Nullable String provider)
            throws GeneralSecurityException;
}
