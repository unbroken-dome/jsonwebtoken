package org.unbrokendome.jsonwebtoken.encoding.payload

import com.fasterxml.jackson.databind.ObjectMapper
import org.unbrokendome.jsonwebtoken.BinaryData
import spock.lang.Specification
import spock.lang.Subject

import java.nio.charset.StandardCharsets


class DefaultPayloadDeserializerTest extends Specification {

    @Subject
    def deserializer = new DefaultPayloadDeserializer(new ObjectMapper())


    def "Deserializing a serialized map should return correct map"() {
        when:
            def map = deserializer.deserialize(
                    BinaryData.of('{"key1":"value1","key2":123,"key3":false}', StandardCharsets.UTF_8),
                    Map)
        then:
            map == ["key1": "value1", "key2": 123, "key3": false]
    }
}
