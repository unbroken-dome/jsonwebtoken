package org.unbrokendome.jsonwebtoken;

public abstract class JwtException extends Exception {

	private static final long serialVersionUID = 8417239244491453485L;


	public JwtException(String message, Throwable cause) {
		super(message, cause);
	}


	public JwtException(String message) {
		super(message);
	}
}
