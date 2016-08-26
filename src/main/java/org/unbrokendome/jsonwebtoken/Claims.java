package org.unbrokendome.jsonwebtoken;

import java.time.Instant;


public interface Claims extends MapData {

    String ISSUER = "iss";
    String SUBJECT = "sub";
    String AUDIENCE = "aud";
    String EXPIRATION = "exp";
    String NOT_BEFORE = "nbf";
    String ISSUED_AT = "iat";
    String ID = "jti";


    default String getIssuer() {
        return getString(ISSUER);
    }


    default String getSubject() {
        return getString(SUBJECT);
    }


    default String getAudience() {
        return getString(AUDIENCE);
    }


    default Instant getExpiration() {
        return getInstant(EXPIRATION);
    }


    default Instant getNotBefore() {
        return getInstant(NOT_BEFORE);
    }


    default Instant getIssuedAt() {
        return getInstant(ISSUED_AT);
    }


    default String getId() {
        return getString(ID);
    }
}
