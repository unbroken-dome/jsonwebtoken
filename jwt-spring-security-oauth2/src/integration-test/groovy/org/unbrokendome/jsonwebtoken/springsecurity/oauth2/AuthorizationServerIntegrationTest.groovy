package org.unbrokendome.jsonwebtoken.springsecurity.oauth2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.unbrokendome.jsonwebtoken.spring.autoconfigure.JwtAutoConfiguration
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure.JwtOAuth2AutoConfiguration
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
@TestPropertySource(properties = [
        'jwt.mode=ENCODE_ONLY',
        'jwt.signing.algorithm=HS256',
        'jwt.signing.key=QYeVQ76sCMPsJ0eyn/MSYlA5ccpR/Bdu',
        'security.oauth2.client.client-id=CLIENT',
        'security.oauth2.client.client-secret=SECRET',
        'security.oauth2.client.scope=SCOPE',
        'logging.level.org.springframework.boot.autoconfigure=DEBUG',
        'logging.level.org.springframework.test.web.servlet.result=DEBUG'
])
class AuthorizationServerIntegrationTest extends Specification {

    @SpringBootConfiguration
    @ImportAutoConfiguration([OAuth2AutoConfiguration, JwtAutoConfiguration, JwtOAuth2AutoConfiguration])
    @EnableAuthorizationServer
    @Import(MockMvcLoggingConfig)
    static class TestApplication {
    }

    @Autowired
    MockMvc mockMvc


    def "test"() {
        given:
            def request = post('/oauth/token')
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header(HttpHeaders.AUTHORIZATION, basicAuthorization('CLIENT', 'SECRET'))
                    .content('grant_type=client_credentials&scope=SCOPE')

        when:
            def result = mockMvc.perform request

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('access_token').exists())
    }


    private static String basicAuthorization(String username, String password) {
        "Basic ${Base64.encoder.encodeToString "$username:$password".getBytes('UTF-8')}"
    }
}
