package org.unbrokendome.jsonwebtoken.encoding.payload

import org.unbrokendome.jsonwebtoken.BinaryData
import spock.lang.Specification
import spock.lang.Subject


public class StringPayloadSerializerTest extends Specification {

    private static final String TEST_STRING = 'Lorem ipsum dolor sit amet'

    @Subject
    private PayloadSerializer<String> serializer = StringPayloadSerializer.instance


    def "Serializing should return equivalent byte array"() {
        when:
			def result = serializer.serialize TEST_STRING
        then:
            result == BinaryData.ofUtf8(TEST_STRING)
    }


    def "Deserializing should return equivalent string"() {
        when:
            def result = serializer.deserialize BinaryData.ofUtf8(TEST_STRING), String
        then:
            result == TEST_STRING
    }
}
