package org.unbrokendome.jsonwebtoken;

import java.time.Instant;


public interface ClaimsBuilder extends MapDataBuilder<ClaimsBuilder, Claims>, Claims {

    default ClaimsBuilder setIssuer(String issuer) {
        return set(ISSUER, issuer);
    }


    default ClaimsBuilder setSubject(String subject) {
        return set(SUBJECT, subject);
    }


    default ClaimsBuilder setAudience(String audience) {
        return set(AUDIENCE, audience);
    }


    default ClaimsBuilder setExpiration(Instant expiration) {
        return set(EXPIRATION, expiration);
    }


    default ClaimsBuilder setNotBefore(Instant notBefore) {
        return set(NOT_BEFORE, notBefore);
    }


    default ClaimsBuilder setIssuedAt(Instant issuedAt) {
        return set(ISSUED_AT, issuedAt);
    }


    default ClaimsBuilder setId(String id) {
        return set(ID, id);
    }
}
