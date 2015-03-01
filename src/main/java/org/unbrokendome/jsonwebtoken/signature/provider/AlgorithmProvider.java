package org.unbrokendome.jsonwebtoken.signature.provider;

import java.security.NoSuchAlgorithmException;

public interface AlgorithmProvider<T> {

	T getInstance() throws NoSuchAlgorithmException;


	void returnInstance(T instance);
}
