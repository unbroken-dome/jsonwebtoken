package org.unbrokendome.jsonwebtoken.encoding

import com.fasterxml.jackson.databind.ObjectMapper
import org.unbrokendome.jsonwebtoken.BinaryData
import org.unbrokendome.jsonwebtoken.impl.DefaultJoseHeaderBuilder
import spock.lang.Specification
import spock.lang.Subject


class DefaultHeaderSerializerTest extends Specification {

    def objectMapper = new ObjectMapper()

    @Subject
    def serializer = new DefaultHeaderSerializer(objectMapper)


    def "Serialize header"() {
        given:
            def header = new DefaultJoseHeaderBuilder()
                    .setAlgorithm("none")
                    .setType("JWT")
                    .build()
        when:
            def bytes = serializer.serialize header
        then:
            bytes == BinaryData.ofUtf8(objectMapper.writeValueAsString(header))
    }
}
