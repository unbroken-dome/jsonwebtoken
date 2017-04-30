package org.unbrokendome.jsonwebtoken;


/**
 * @deprecated Use {@link Jwt} instead
 */
@Deprecated
public class Jwts {

    private Jwts() {
        // Private constructor prevents instantiation
    }


    public static JoseHeaderBuilder header() {
        return Jwt.header();
    }


    public static ClaimsBuilder claims() {
        return Jwt.claims();
    }


    public static JwtProcessorBuilder processor() {
        return Jwt.processor();
    }
}
