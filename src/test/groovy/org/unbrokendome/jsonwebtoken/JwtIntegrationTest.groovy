package org.unbrokendome.jsonwebtoken

import com.fasterxml.jackson.databind.ObjectMapper
import org.unbrokendome.jsonwebtoken.encoding.DefaultHeaderSerializer
import org.unbrokendome.jsonwebtoken.encoding.JwsCompactEncoder
import org.unbrokendome.jsonwebtoken.encoding.payload.DefaultPayloadSerializer
import org.unbrokendome.jsonwebtoken.signature.JwsUnsupportedAlgorithmException
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import spock.lang.Specification

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset


class JwtIntegrationTest extends Specification {

    final objectMapper = new ObjectMapper()

    final Clock clock = Clock.fixed(Instant.parse("2017-04-30T14:22:28Z"), ZoneOffset.UTC)

    static final SecretKey SECRET_KEY = new SecretKeySpec(
            Base64.getDecoder().decode("0NGVvKq99Ew17WICWnp91OyNLGreCpQYfdn7ctDSIR8="),
            "HmacSHA256")


    def "Encode and decode claims"() {
        given:
            def processor = Jwt.processor()
                    .signAndVerifyWith(SignatureAlgorithms.HS256, SECRET_KEY)
                    .build()
            def claims = buildClaims()

        when:
            def jwt = processor.encode claims
            def decodedClaims = processor.decodeClaims jwt

        then:
            decodedClaims.asMap() == claims.asMap()
    }


    def "Encode only"() {
        given:
            def processor = Jwt.processor().encodeOnly()
                    .signWith(SignatureAlgorithms.HS256, SECRET_KEY)
                    .build()
            def claims = buildClaims()

        when:
            def jwt = processor.encode claims

        then:
            jwt != null
    }


    def "Decode only"() {
        given:
            def processor = Jwt.processor().decodeOnly()
                    .verifyWith(SignatureAlgorithms.HS256, SECRET_KEY)
                    .build()
            def expectedClaims = buildClaims()
            def token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUaWxsIiwiaWF0IjoxNDkzNTYyMTQ4LCJleHAiOjE0OTM1NjI0NDh9.lRYWKfHqRJiu_uG3tAX7O8E9aHpSiGPe2q1ZD5h2qAw='

        when:
            def claims = processor.decodeClaims token

        then:
            claims.asMap() == expectedClaims.asMap()
    }


    def "Cannot forge claims using NONE algorithm"() {
        given:
            def processor = Jwt.processor()
                    .signAndVerifyWith(SignatureAlgorithms.HS256, SECRET_KEY)
                    .build()
            def originalClaims = buildClaims()

        and:
            def forgedHeader = Jwt.header()
                    .setAlgorithm("NONE")
                    .build()
            def forgedClaims = Jwt.claims()
                    .setSubject(originalClaims.subject)
                    .setIssuedAt(originalClaims.issuedAt)
                    .setExpiration(originalClaims.expiration)
                    .setAudience("restricted")
            def forgedToken = new JwsCompactEncoder().encode(
                    new DefaultHeaderSerializer(objectMapper).serialize(forgedHeader),
                    new DefaultPayloadSerializer(objectMapper).serialize(forgedClaims.asMap()),
                    null)

        when:
            processor.decodeClaims forgedToken

        then:
            thrown JwsUnsupportedAlgorithmException
    }


    private Claims buildClaims() {
        Jwt.claims()
                .setSubject("Till")
                .setIssuedAt(clock.instant())
                .setExpiration(clock.instant().plusSeconds(300))
                .build()
    }
}
