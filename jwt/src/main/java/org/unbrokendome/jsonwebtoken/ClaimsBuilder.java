package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Set;


public interface ClaimsBuilder extends MapDataBuilder<ClaimsBuilder, Claims>, Claims {

    /**
     * Sets the value of the issuer (<code>iss</code>) claim.
     *
     * @param issuer the value of the issuer claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getIssuer()
     */
    @Nonnull
    default ClaimsBuilder setIssuer(String issuer) {
        return set(ISSUER, issuer);
    }


    /**
     * Sets the value of the subject (<code>sub</code>) claim.
     *
     * @param subject the value of the subject claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getSubject()
     */
    @Nonnull
    default ClaimsBuilder setSubject(String subject) {
        return set(SUBJECT, subject);
    }


    /**
     * Sets the value of the audience (<code>aud</code>) claim to a single string.
     *
     * @param audience the value of the audience claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getAudience()
     */
    @Nonnull
    default ClaimsBuilder setAudience(String audience) {
        return set(AUDIENCE, audience);
    }


    /**
     * Sets the value of the audience (<code>aud</code>) claim to a set of strings.
     *
     * @param audiences the value of the audience claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getAudiences()
     */
    @Nonnull
    default ClaimsBuilder setAudiences(Set<String> audiences) {
        return set(AUDIENCE, audiences);
    }


    /**
     * Sets the value of the expiration time (<code>exp</code>) claim.
     *
     * @param expiration the value of the expiration time claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getExpiration()
     */
    @Nonnull
    default ClaimsBuilder setExpiration(Instant expiration) {
        return set(EXPIRATION, expiration);
    }


    /**
     * Sets the value of the not-before (<code>nbf</code>) claim.
     *
     * @param notBefore the value of the not-before claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getNotBefore()
     */
    @Nonnull
    default ClaimsBuilder setNotBefore(Instant notBefore) {
        return set(NOT_BEFORE, notBefore);
    }


    /**
     * Sets the value of the issued-at (<code>iat</code>) claim.
     *
     * @param issuedAt the value of the issued-at claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getIssuedAt()
     */
    @Nonnull
    default ClaimsBuilder setIssuedAt(Instant issuedAt) {
        return set(ISSUED_AT, issuedAt);
    }


    /**
     * Sets the value of the JWT ID (<code>jti</code>) claim.
     *
     * @param id the value of the JWT ID claim
     * @return the current {@link ClaimsBuilder} instance
     * @see Claims#getId()
     */
    @Nonnull
    default ClaimsBuilder setId(String id) {
        return set(ID, id);
    }
}
