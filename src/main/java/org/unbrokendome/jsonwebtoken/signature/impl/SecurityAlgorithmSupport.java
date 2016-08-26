package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;


public abstract class SecurityAlgorithmSupport<T> {

    private final AlgorithmProvider<T> provider;


    public SecurityAlgorithmSupport(AlgorithmProvider<T> provider) {
        this.provider = provider;
    }


    protected final <R> R doWithAlgorithmSafely(SafeAlgorithmCallback<T, R> callback) {
        T instance = getAlgorithmInstance();
        try {
            return callback.callback(instance);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e);
        } finally {
            provider.returnInstance(instance);
        }
    }


    protected final <R> R doWithAlgorithm(AlgorithmCallback<T, R> callback) throws SignatureException {
        T instance = getAlgorithmInstance();
        try {
            return callback.callback(instance);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e);
        } finally {
            provider.returnInstance(instance);
        }
    }


    private T getAlgorithmInstance() {
        try {
            return provider.getInstance();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Signature algorithm not supported");
        }
    }


    protected interface AlgorithmCallback<T, R> {

        R callback(T instance) throws InvalidKeyException, SignatureException;
    }


    protected interface SafeAlgorithmCallback<T, R> {

        R callback(T instance) throws InvalidKeyException;
    }
}
