package org.unbrokendome.jsonwebtoken.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import spock.lang.Specification

import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

import static org.hamcrest.Matchers.not
import static org.unbrokendome.jsonwebtoken.spring.SpringMatchers.hasBeanOfType


@SuppressWarnings("GroovyAssignabilityCheck")
class DecodeOnlyConfigurationTest extends Specification {

    @Configuration
    @EnableJwtProcessing(mode = JwtProcessorMode.DECODE_ONLY)
    static class TestConfig implements JwtProcessorConfigurer<JwtDecodeOnlyProcessorBuilder> {

        @Bean
        SecretKey secretKey() {
            KeyGenerator.getInstance("HmacSHA256").generateKey()
        }

        @Override
        void configure(JwtDecodeOnlyProcessorBuilder builder) {
            builder.verifyWith(SignatureAlgorithms.HS256, secretKey())
        }
    }


    def "Spring application context should load"() {
        given:
            def applicationContext = new AnnotationConfigApplicationContext()
            applicationContext.register(TestConfig)

        when:
            applicationContext.refresh()

        then:
            applicationContext hasBeanOfType(JwtDecodingProcessor)
        and:
            applicationContext not(hasBeanOfType(JwtEncodingProcessor))
    }
}
