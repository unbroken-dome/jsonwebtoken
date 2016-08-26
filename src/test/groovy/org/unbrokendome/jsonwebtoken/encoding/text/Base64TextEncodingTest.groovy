package org.unbrokendome.jsonwebtoken.encoding.text

import org.unbrokendome.jsonwebtoken.BinaryData
import spock.lang.Specification
import spock.lang.Subject


public class Base64TextEncodingTest extends Specification {

    private static final byte[] TEST_DATA_BYTES = [18, 119, 101, -110, 23, 1, 53, 15, -27, 54, 21, 31, -33,
                                                   -28, 90, 24, 7, 56, -44, 120]

    @Subject
    private TextEncoding encoding = Base64TextEncoding.BASE64_URL


    def "Encode to Base64"() {
        when:
            def result = encoding.encoder.apply BinaryData.of(TEST_DATA_BYTES)
        then:
            result == Base64.urlEncoder.encodeToString(TEST_DATA_BYTES)
    }


    def "Encode empty bytes"() {
        when:
            def result = encoding.encoder.apply BinaryData.EMPTY
        then:
            result == ""
    }


    def "Decode from Base64"() {
        given:
            def encoded = Base64.urlEncoder.encodeToString(TEST_DATA_BYTES)
        when:
            def result = encoding.decoder.apply encoded
        then:
            result == BinaryData.of(TEST_DATA_BYTES)
    }


    def "Decode empty string"() {
        when:
            def result = encoding.decoder.apply ""
        then:
            result == BinaryData.EMPTY
    }
}
