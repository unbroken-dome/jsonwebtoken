package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nullable;
import java.time.Clock;
import java.time.Instant;
import java.util.Set;


/**
 * Contains a collection of claims that are stored in the JWT.
 *
 * <p>This interface offers convenience methods to access the values of the standard claims
 * defined in RFC 7519.
 */
public interface Claims extends MapData {

    String ISSUER = "iss";
    String SUBJECT = "sub";
    String AUDIENCE = "aud";
    String EXPIRATION = "exp";
    String NOT_BEFORE = "nbf";
    String ISSUED_AT = "iat";
    String ID = "jti";


    /**
     * Gets the issuer (<code>iss</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.1">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "iss" (issuer) claim identifies the principal that issued the
     * JWT. The processing of this claim is generally application specific.
     * The "iss" value is a case-sensitive string containing a StringOrURI
     * value. Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the issuer claim, or <code>null</code> if not set
     */
    @Nullable
    default String getIssuer() {
        return getString(ISSUER);
    }


    /**
     * Gets the subject (<code>sub</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.2">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "sub" (subject) claim identifies the principal that is the
     * subject of the JWT. The claims in a JWT are normally statements
     * about the subject. The subject value MUST either be scoped to be
     * locally unique in the context of the issuer or be globally unique.
     * The processing of this claim is generally application specific. The
     * "sub" value is a case-sensitive string containing a StringOrURI
     * value. Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the subject claim, or <code>null</code> if not set
     */
    @Nullable
    default String getSubject() {
        return getString(SUBJECT);
    }


    /**
     * Gets the audience (<code>aud</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.2">RFC 7519</a>.
     *
     * <p>This is a convenience method to retrieve the single audience identifier
     * if it is a simple string, or the first element if it is a set. This method
     * should only be used if the application only ever issues and processes tokens
     * with a single audience.</p>
     *
     * @return the value (or the first element) of the audience claim, or
     * <code>null</code> if not set
     * @see #getAudiences()
     */
    @Nullable
    default String getAudience() {
        Set<String> audiences = getAudiences();
        if (audiences != null && !audiences.isEmpty()) {
            return audiences.iterator().next();
        }
        return null;
    }


    /**
     * Gets the audience (<code>aud</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.3">RFC 7519</a>.
     *
     * <p>In general, there can be multiple audience identifiers, so the value
     * is returned as a set of strings. </p>
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "aud" (audience) claim identifies the recipients that the JWT is
     * intended for. Each principal intended to process the JWT MUST
     * identify itself with a value in the audience claim. If the principal
     * processing the claim does not identify itself with a value in the
     * "aud" claim when this claim is present, then the JWT MUST be
     * rejected. In the general case, the "aud" value is an array of case-
     * sensitive strings, each containing a StringOrURI value. In the
     * special case when the JWT has one audience, the "aud" value MAY be a
     * single case-sensitive string containing a StringOrURI value. The
     * interpretation of audience values is generally application specific.
     * Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the audience claim, or <code>null</code> if not set
     * @see #getAudience()
     */
    @Nullable
    default Set<String> getAudiences() {
        return getStringSet(AUDIENCE);
    }


    /**
     * Gets the expiration time (<code>exp</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.4">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "exp" (expiration time) claim identifies the expiration time on
     * or after which the JWT MUST NOT be accepted for processing. The
     * processing of the "exp" claim requires that the current date/time
     * MUST be before the expiration date/time listed in the "exp" claim.
     * Implementers MAY provide for some small leeway, usually no more than
     * a few minutes, to account for clock skew. Its value MUST be a number
     * containing a NumericDate value. Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the expiration time claim as an {@link Instant}, or
     * <code>null</code> if not set
     */
    @Nullable
    default Instant getExpiration() {
        return getInstant(EXPIRATION);
    }


    /**
     * Gets the not-before (<code>nbf</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.5">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "nbf" (not before) claim identifies the time before which the JWT
     * MUST NOT be accepted for processing. The processing of the "nbf"
     * claim requires that the current date/time MUST be after or equal to
     * the not-before date/time listed in the "nbf" claim. Implementers MAY
     * provide for some small leeway, usually no more than a few minutes, to
     * account for clock skew. Its value MUST be a number containing a
     * NumericDate value. Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the not-before claim as an {@link Instant}, or
     * <code>null</code> if not set
     */
    @Nullable
    default Instant getNotBefore() {
        return getInstant(NOT_BEFORE);
    }


    /**
     * Gets the issued-at (<code>iat</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.6">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "iat" (issued at) claim identifies the time at which the JWT was
     * issued. This claim can be used to determine the age of the JWT. Its
     * value MUST be a number containing a NumericDate value. Use of this
     * claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the issued-at claim as an {@link Instant}, or
     * <code>null</code> if not set
     */
    @Nullable
    default Instant getIssuedAt() {
        return getInstant(ISSUED_AT);
    }


    /**
     * Gets the JWT ID (<code>jti</code>) claim, as defined in
     * <a href="https://tools.ietf.org/html/rfc7519#section-4.1.7">RFC 7519</a>.
     *
     * <p>From the specification in RFC 7519:
     *
     * <blockquote>The "jti" (JWT ID) claim provides a unique identifier for the JWT.
     * The identifier value MUST be assigned in a manner that ensures that
     * there is a negligible probability that the same value will be
     * accidentally assigned to a different data object; if the application
     * uses multiple issuers, collisions MUST be prevented among values
     * produced by different issuers as well. The "jti" claim can be used
     * to prevent the JWT from being replayed. The "jti" value is a case-
     * sensitive string. Use of this claim is OPTIONAL.
     * </blockquote>
     *
     * @return the value of the issued-at claim as an {@link Instant}, or
     * <code>null</code> if not set
     */
    @Nullable
    default String getId() {
        return getString(ID);
    }


    /**
     * Checks whether this set of claims is expired, according to the given {@link Clock}.
     * <p>
     * Returns {@code false} if this instance does not contain an {@linkplain #EXPIRATION expiration} claim.
     *
     * @param clock a {@link Clock} that provides the current time
     * @return {@code true} if this set of claims is expired; {@code false} if not
     */
    default boolean isExpired(Clock clock) {
        Instant expiration = getExpiration();
        return expiration != null && expiration.isBefore(clock.instant());
    }
}
