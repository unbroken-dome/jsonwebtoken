package org.unbrokendome.jsonwebtoken

import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import spock.lang.Specification

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.time.Instant


public class JwtIntegrationTest extends Specification {

    static final SecretKey SIGN_KEY = new SecretKeySpec(
            Base64.getDecoder().decode("0NGVvKq99Ew17WICWnp91OyNLGreCpQYfdn7ctDSIR8="),
            "HmacSHA256")


    def "Encode and decode claims"() {
        given:
            def processor = Jwts.processor()
                    .signAndVerifyWith(SignatureAlgorithms.HS256, SIGN_KEY)
                    .build()
            def claims = Jwts.claims()
                    .setSubject("Till")
                    .setIssuedAt(Instant.now())
                    .setExpiration(Instant.now().plusSeconds(300))
                    .build()
        when:
            def jwt = processor.encode claims
            def decodedClaims = processor.decodeClaims jwt

        then:
            decodedClaims.asMap() == claims.asMap()
    }
}
