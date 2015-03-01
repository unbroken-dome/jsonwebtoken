package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.JwtException;

public class JwtMalformedTokenException extends JwtException {

	private static final long serialVersionUID = -3953815089602358758L;


	public JwtMalformedTokenException(String message, Throwable cause) {
		super(message, cause);
	}


	public JwtMalformedTokenException(String message) {
		super(message);
	}
}
