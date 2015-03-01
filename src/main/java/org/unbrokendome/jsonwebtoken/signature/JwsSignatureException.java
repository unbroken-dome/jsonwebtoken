package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.JwtException;

public class JwsSignatureException extends JwtException {

	private static final long serialVersionUID = -3357880468547356469L;


	public JwsSignatureException(String message, Throwable cause) {
		super(message, cause);
	}


	public JwsSignatureException(String message) {
		super(message);
	}
}
