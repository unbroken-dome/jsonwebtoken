package org.unbrokendome.jsonwebtoken.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import spock.lang.Specification

import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

import static org.hamcrest.Matchers.not
import static org.unbrokendome.jsonwebtoken.spring.SpringMatchers.hasBeanOfType


@SuppressWarnings("GroovyAssignabilityCheck")
class EncodeOnlyConfigurationTest extends Specification {

    @Configuration
    @EnableJwtProcessing(mode = JwtProcessorMode.ENCODE_ONLY)
    static class TestConfig implements JwtProcessorConfigurer<JwtEncodeOnlyProcessorBuilder> {

        @Bean
        SecretKey secretKey() {
            KeyGenerator.getInstance("HmacSHA256").generateKey()
        }

        @Override
        void configure(JwtEncodeOnlyProcessorBuilder builder) {
            builder.signWith(SignatureAlgorithms.HS256, secretKey())
        }
    }


    def "Spring application context should load"() {
        given:
            def applicationContext = new AnnotationConfigApplicationContext()
            applicationContext.register(TestConfig)

        when:
            applicationContext.refresh()

        then:
            applicationContext hasBeanOfType(JwtEncodingProcessor)
        and:
            applicationContext not(hasBeanOfType(JwtDecodingProcessor))
    }
}
