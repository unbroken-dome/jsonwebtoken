package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.signature.provider.AlgorithmProvider;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.SignatureException;


@SuppressWarnings("Duplicates")
abstract class SecurityAlgorithmSupport<T> {

    private final AlgorithmProvider<T> provider;


    SecurityAlgorithmSupport(AlgorithmProvider<T> provider) {
        this.provider = provider;
    }


    final <R> R doWithAlgorithmSafely(SafeAlgorithmCallback<T, R> callback) {
        T instance = getAlgorithmInstance();
        try {
            return callback.callback(instance);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e);
        } finally {
            provider.returnInstance(instance);
        }
    }


    final <R> R doWithAlgorithm(AlgorithmCallback<T, R> callback) throws SignatureException {
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
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Signature algorithm not supported", e);
        }
    }


    protected interface AlgorithmCallback<T, R> {

        R callback(T instance) throws InvalidKeyException, SignatureException;
    }


    protected interface SafeAlgorithmCallback<T, R> {

        R callback(T instance) throws InvalidKeyException;
    }
}
