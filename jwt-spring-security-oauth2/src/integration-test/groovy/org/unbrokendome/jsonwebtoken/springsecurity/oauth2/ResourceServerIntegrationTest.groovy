package org.unbrokendome.jsonwebtoken.springsecurity.oauth2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.unbrokendome.jsonwebtoken.Jwt
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import org.unbrokendome.jsonwebtoken.spring.autoconfigure.JwtAutoConfiguration
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure.JwtOAuth2AutoConfiguration
import spock.lang.Specification

import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@TestPropertySource(properties = [
        'jwt.mode=DECODE_ONLY',
        'jwt.verification[0].algorithm=ES256',
        'jwt.verification[0].key=MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErJ5rKbK22KrkKWF1boj6bhqzRVb/34jrq+vCxTwr/iUPusRxzF7kwJKNSOsmcgmnRwQGbqiip4SlZdRFhiVe+A==',
        'security.oauth2.client.client-id=CLIENT',
        'security.oauth2.client.grant-type=client_credentials',
        //'security.oauth2.resource.user-info-uri=/',
        'logging.level.org.springframework.security=DEBUG',
        'logging.level.org.springframework.test.web.servlet.result=DEBUG'
])
class ResourceServerIntegrationTest extends Specification {

    @EnableAutoConfiguration
    @ImportAutoConfiguration([OAuth2AutoConfiguration, JwtAutoConfiguration, JwtOAuth2AutoConfiguration])
    @EnableResourceServer
    @Import(MockMvcLoggingConfig)
    static class TestApplication {

        @Bean
        HelloController helloController() {
            new HelloController()
        }
    }

    @Autowired
    MockMvc mockMvc


    def "test"() {
        given:
            def token = generateAccessToken()
            def request = get('/hello')
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $token")

        when:
            def result = mockMvc.perform request

        then:
            result.andExpect(status().isOk())
            result.andExpect(content().string('Hello'))
    }


    private String generateAccessToken() {
        def privateKeyBytes = Base64.mimeDecoder.decode('''
            MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgcs7rrcFpX9+iCjxg
            vWTQpszK2dnkfUTcjiQuvKvqx3ihRANCAASsnmspsrbYquQpYXVuiPpuGrNFVv/f
            iOur68LFPCv+JQ+6xHHMXuTAko1I6yZyCadHBAZuqKKnhKVl1EWGJV74
        ''')
        def kf = KeyFactory.getInstance('EC')
        def privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes))

        JwtEncodingProcessor processor = Jwt.processor().encodeOnly()
                .signWith(SignatureAlgorithms.ES256, privateKey)
                .build()

        def claims = Jwt.claims()
                .setSubject('Till')
                .build()

        return processor.encode(claims)
    }

}
