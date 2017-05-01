package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.GeneralSecurityException;


public interface AlgorithmProvider<T> {

    T getInstance() throws GeneralSecurityException;


    void returnInstance(T instance);
}
