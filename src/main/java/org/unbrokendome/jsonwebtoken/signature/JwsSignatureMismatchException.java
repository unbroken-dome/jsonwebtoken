package org.unbrokendome.jsonwebtoken.signature;

public class JwsSignatureMismatchException extends JwsSignatureException {

	private static final long serialVersionUID = 6457883089369705240L;


	public JwsSignatureMismatchException(String message, Throwable cause) {
		super(message, cause);
	}


	public JwsSignatureMismatchException(String message) {
		super(message);
	}
}
