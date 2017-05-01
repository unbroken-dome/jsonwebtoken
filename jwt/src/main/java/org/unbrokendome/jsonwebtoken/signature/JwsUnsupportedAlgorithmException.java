package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JwtException;


public class JwsUnsupportedAlgorithmException extends JwtException {

    private static final long serialVersionUID = 3881890784737292415L;

    private final String algorithmName;


    public JwsUnsupportedAlgorithmException(String algorithmName) {
        super("Algorithm '" + algorithmName + "' is not supported/allowed");
        this.algorithmName = algorithmName;
    }


    public String getAlgorithmName() {
        return algorithmName;
    }
}
