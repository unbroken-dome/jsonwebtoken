package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.impl.DefaultClaimsBuilder;
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.impl.DefaultJwtProcessorBuilder;


/**
 * Main entry point to the JSON Web Token library.
 */
public final class Jwt {

    private Jwt() {
        // Private constructor prevents instantiation
    }


    /**
     * Starts constructing a JOSE header for a token.
     *
     * @return a new {@link JoseHeaderBuilder} instance
     */
    public static JoseHeaderBuilder header() {
        return new DefaultJoseHeaderBuilder();
    }


    /**
     * Starts constructing claims for a token.
     *
     * @return a new {@link ClaimsBuilder} instance
     */
    public static ClaimsBuilder claims() {
        return new DefaultClaimsBuilder();
    }


    /**
     * Starts configuration of a {@link JwtProcessor} instance.
     *
     * @return a new {@link JwtProcessorBuilder} that can be used to configure and construct the {@link JwtProcessor}
     */
    public static JwtProcessorBuilder processor() {
        return new DefaultJwtProcessorBuilder();
    }
}
