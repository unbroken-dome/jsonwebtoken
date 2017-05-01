package org.unbrokendome.jsonwebtoken.encoding

import com.fasterxml.jackson.databind.ObjectMapper
import org.unbrokendome.jsonwebtoken.BinaryData
import org.unbrokendome.jsonwebtoken.JoseHeader
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder
import spock.lang.Specification
import spock.lang.Subject


class DefaultHeaderDeserializerTest extends Specification {

    static final JoseHeader TEST_HEADER = new DefaultJoseHeaderBuilder()
            .setAlgorithm("none")
            .setType("JWT")
            .build()

    @Subject
    def deserializer = new DefaultHeaderDeserializer(new ObjectMapper())


    def "Deserialize header"() {
        when:
            def header = deserializer.deserialize BinaryData.ofUtf8('{"alg":"none","typ":"JWT"}')
        then:
            header != null
            header.algorithm == "none"
            header.type == "JWT"
    }


    def "Deserializing header should throw exception for malformed input"() {
        when:
            deserializer.deserialize BinaryData.ofUtf8("qr9etug4ehß9345")
        then:
            thrown(JwtMalformedTokenException)
    }
}
