package org.unbrokendome.jsonwebtoken.springsecurity.oauth2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
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
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

import static org.hamcrest.Matchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG, printOnlyOnFailure = false)
@TestPropertySource(properties = [
        'jwt.mode=DECODE_ONLY',
        'jwt.verification[0].algorithm=ES256',
        'jwt.verification[0].key=MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErJ5rKbK22KrkKWF1boj6bhqzRVb/34jrq+vCxTwr/iUPusRxzF7kwJKNSOsmcgmnRwQGbqiip4SlZdRFhiVe+A==',
        'security.oauth2.client.client-id=CLIENT',
        'security.oauth2.client.grant-type=client_credentials',
        'logging.level.org.springframework.boot.autoconfigure=DEBUG',
        'logging.level.org.springframework.test.web.servlet.result=DEBUG'
])
class ResourceServerIntegrationTest extends Specification {

    static final Instant INSTANT = Instant.parse('2017-08-31T19:22:35Z')


    @SpringBootConfiguration()
    @ImportAutoConfiguration(
            classes = [JwtAutoConfiguration, JwtOAuth2AutoConfiguration],
            exclude = OAuth2AutoConfiguration)
    @EnableResourceServer
    static class TestApplication {

        @Bean
        Clock clock() {
            Clock.fixed(INSTANT, ZoneOffset.UTC)
        }

        @Bean
        HelloController helloController() {
            new HelloController()
        }
    }

    @Autowired
    MockMvc mockMvc


    def "Resource request should be authorized when access token is valid"() {
        given:
            def token = generateAccessToken()
            def request = get('/hello')
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $token")

        when:
            def result = mockMvc.perform request

        then:
            result.andExpect status().isOk()
            result.andExpect content().string('Hello')
    }


    def "Resource request should not be authorized if no access token is given"() {
        given:
            def request = get('/hello')

        when:
            def result = mockMvc.perform request

        then:
            result.andExpect status().isUnauthorized()
        and:
            result.andExpect jsonPath('$.error', equalTo('unauthorized'))
    }


    def "Resource request should not be authorized if access token is expired"() {
        given:
            def token = generateAccessToken(true)
            def request = get('/hello')
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $token")

        when:
            def result = mockMvc.perform request

        then:
            result.andExpect status().isUnauthorized()
        and:
            result.andExpect jsonPath('$.error', equalTo('invalid_token'))
    }




    private String generateAccessToken(boolean expired = false) {
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
                .setExpiration(expired ? INSTANT.minusSeconds(60) : INSTANT.plusSeconds(60))
                .build()

        return processor.encode(claims)
    }

}
