package org.unbrokendome.jsonwebtoken.springsecurity.oauth2

import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.unbrokendome.jsonwebtoken.spring.autoconfigure.JwtAutoConfiguration
import org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure.JwtOAuth2AutoConfiguration
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG, printOnlyOnFailure = false)
@TestPropertySource(properties = [
        'jwt.mode=FULL',
        'jwt.signing.algorithm=HS256',
        'jwt.signing.key=QYeVQ76sCMPsJ0eyn/MSYlA5ccpR/Bdu',
        'security.oauth2.client.client-id=CLIENT',
        'security.oauth2.client.client-secret=SECRET',
        'security.oauth2.client.scope=SCOPE',
        'logging.level.org.springframework.test.web.servlet.result=DEBUG',
        'logging.level.org.springframework.security=DEBUG'
])
class CombinedServerIntegrationTest extends Specification {

    @SpringBootConfiguration
    @ImportAutoConfiguration(
            classes = [JwtAutoConfiguration, JwtOAuth2AutoConfiguration],
            exclude = OAuth2AutoConfiguration)
    @EnableAuthorizationServer
    @EnableResourceServer
    static class TestApplication {

        @Bean
        HelloController helloController() {
            new HelloController()
        }
    }


    @Autowired
    MockMvc mockMvc


    def "Obtain token from authorization server"() {
        given:
            def tokenRequest = createTokenRequest()

        when:
            def result = mockMvc.perform tokenRequest

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('access_token').exists())
    }


    def "Use token to access resource"() {
        given:
            def tokenRequest = createTokenRequest()
            def tokenResult = mockMvc.perform(tokenRequest).andReturn()
            String accessToken = JsonPath.read(tokenResult.response.contentAsString, 'access_token')

        and:
            def resourceRequest = get('/hello')
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")

        when:
            def result = mockMvc.perform resourceRequest

        then:
            result.andExpect status().isOk()
            result.andExpect content().string('Hello')
    }


    private MockHttpServletRequestBuilder createTokenRequest() {
        post('/oauth/token')
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, basicAuthorization('CLIENT', 'SECRET'))
                .content('grant_type=client_credentials&scope=SCOPE')
    }


    private static String basicAuthorization(String username, String password) {
        "Basic ${Base64.encoder.encodeToString "$username:$password".getBytes('UTF-8')}"
    }
}
