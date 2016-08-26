package org.unbrokendome.jsonwebtoken.signature.provider;

import nf.fr.eraasoft.pool.ObjectPool;
import nf.fr.eraasoft.pool.PoolException;
import nf.fr.eraasoft.pool.PoolSettings;
import nf.fr.eraasoft.pool.PoolableObject;

import java.security.NoSuchAlgorithmException;


public abstract class AbstractPooledAlgorithmProvider<T> implements AlgorithmProvider<T> {

    private final ObjectPool<T> pool;


    public AbstractPooledAlgorithmProvider(PoolableObject<T> poolable, PoolConfigurer poolConfigurer) {
        PoolSettings<T> poolSettings = new PoolSettings<>(poolable);
        poolConfigurer.configure(poolSettings);

        this.pool = poolSettings.pool();
    }


    @Override
    public T getInstance() throws NoSuchAlgorithmException {
        try {
            return pool.getObj();
        } catch (PoolException e) {
            if (e.getCause() instanceof NoSuchAlgorithmException) {
                throw (NoSuchAlgorithmException) e.getCause();
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void returnInstance(T instance) {
        pool.returnObj(instance);
    }
}
