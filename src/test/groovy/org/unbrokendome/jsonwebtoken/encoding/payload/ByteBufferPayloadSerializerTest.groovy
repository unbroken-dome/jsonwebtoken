package org.unbrokendome.jsonwebtoken.encoding.payload

import org.unbrokendome.jsonwebtoken.BinaryData
import spock.lang.Specification
import spock.lang.Subject

import java.nio.ByteBuffer


class ByteBufferPayloadSerializerTest extends Specification {

    private static final byte[] TEST_DATA_BYTES = [18, 119, 101, -110, 23, 1, 53, 15, -27, 54, 21, 31, -33,
                                                   -28, 90, 24, 7, 56, -44, 120]

    @Subject
    private def serializer = ByteBufferPayloadSerializer.instance


    def "Serializing should return equivalent BinaryData"() {
        when:
            def result = serializer.serialize ByteBuffer.wrap(TEST_DATA_BYTES)
        then:
            result == BinaryData.of(TEST_DATA_BYTES)
    }


    def "Deserializing should return equivalent ByteBuffer"() {
        when:
            ByteBuffer result = serializer.deserialize BinaryData.of(TEST_DATA_BYTES), ByteBuffer
        then:
            result == ByteBuffer.wrap(TEST_DATA_BYTES)
    }
}
