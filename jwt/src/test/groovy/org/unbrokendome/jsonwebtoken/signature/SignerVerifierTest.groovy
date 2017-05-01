package org.unbrokendome.jsonwebtoken.signature

import org.unbrokendome.jsonwebtoken.BinaryData
import org.unbrokendome.jsonwebtoken.signature.impl.SignerVerifier
import spock.lang.Specification
import spock.lang.Subject

import javax.crypto.spec.SecretKeySpec
import java.security.Key


class SignerVerifierTest extends Specification {

    static final Key TEST_KEY = new SecretKeySpec(
            Base64.decoder.decode('0NGVvKq99Ew17WICWnp91OyNLGreCpQYfdn7ctDSIR8='),
            "HmacSHA256")

    def signer = Mock(Signer)

    @Subject
    def verifier = new SignerVerifier(signer)


    def "Matching signature should be verified"() {
        given:
            def header = BinaryData.of(0x039ABF3C23AF034AL)
            def payload = BinaryData.of(0x572B49AC03991E98L)
            def correctSignature = BinaryData.of(0xDEADBEEF)

            signer.sign(header, payload, TEST_KEY) >> correctSignature

        when:
            verifier.verify header, payload, correctSignature, TEST_KEY

        then:
            noExceptionThrown()
    }


    def "Should throw exception on signature mismatch"() {
        given:
            def header = BinaryData.of(0x039ABF3C23AF034AL)
            def payload = BinaryData.of(0x572B49AC03991E98L)
            def correctSignature = BinaryData.of(0xDEADBEEF)
            def wrongSignature = BinaryData.of(0x0BADF00D)

            signer.sign(header, payload, TEST_KEY) >> correctSignature
        when:
            verifier.verify header, payload, wrongSignature, TEST_KEY
        then:
            thrown(JwsSignatureMismatchException)
    }
}
