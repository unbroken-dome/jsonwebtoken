package org.unbrokendome.jsonwebtoken;

public interface JoseHeader extends MapData {

	String TYPE = "typ";
	String CONTENT_TYPE = "cty";
	String ALGORITHM = "alg";
	String JWK_SET_URL = "jku";
	String JSON_WEB_KEY = "jwk";
	String KEY_ID = "kid";
	String X509_URL = "x5u";
	String X509_CERT_CHAIN = "x5c";
	String X509_CERT_SHA1_THUMBPRINT = "x5t";
	String X509_CERT_SHA256_THUMBPRINT = "x5t#S256";
	String CRITICAL = "crit";


	default String getType() {
		return getString(TYPE);
	}


	default String getContentType() {
		return getString(CONTENT_TYPE);
	}


	default String getAlgorithm() {
		return getString(ALGORITHM);
	}


	default String getKeyId() {
		return getString(KEY_ID);
	}
}
