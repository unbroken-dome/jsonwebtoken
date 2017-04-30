package org.unbrokendome.jsonwebtoken;

public abstract class JwtException extends Exception {

    private static final long serialVersionUID = 8417239244491453485L;


    protected JwtException(String message, Throwable cause) {
        super(message, cause);
    }


    protected JwtException(String message) {
        super(message);
    }
}
