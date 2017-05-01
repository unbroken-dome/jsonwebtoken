package org.unbrokendome.jsonwebtoken.encoding.payload

import com.fasterxml.jackson.databind.ObjectMapper
import org.unbrokendome.jsonwebtoken.BinaryData
import spock.lang.Specification
import spock.lang.Subject

import java.nio.charset.StandardCharsets


class DefaultPayloadSerializerTest extends Specification {

    @Subject
    def serializer = new DefaultPayloadSerializer(new ObjectMapper())


    def "Serializing a map should return correct bytes"() {
        when:
            def bytes = serializer.serialize("key1": "value1", "key2": 123, "key3": false)
        then:
            bytes == BinaryData.of('{"key1":"value1","key2":123,"key3":false}', StandardCharsets.UTF_8)
    }



}
