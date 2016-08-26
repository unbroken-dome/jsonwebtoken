package org.unbrokendome.jsonwebtoken;

import org.unbrokendome.jsonwebtoken.impl.DefaultClaimsBuilder;
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder;
import org.unbrokendome.jsonwebtoken.impl.DefaultJwtProcessorBuilder;


public class Jwts {

    public static JoseHeaderBuilder header() {
        return new DefaultJoseHeaderBuilder();
    }


    public static ClaimsBuilder claims() {
        return new DefaultClaimsBuilder();
    }


    public static JwtProcessorBuilder processor() {
        return new DefaultJwtProcessorBuilder();
    }
}
