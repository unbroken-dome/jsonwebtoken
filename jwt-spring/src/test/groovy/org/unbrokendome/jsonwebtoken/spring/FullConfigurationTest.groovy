package org.unbrokendome.jsonwebtoken.spring

import org.spockframework.util.Matchers
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor
import org.unbrokendome.jsonwebtoken.JwtProcessor
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms
import spock.lang.Specification

import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

import static org.unbrokendome.jsonwebtoken.spring.SpringMatchers.hasBeanOfType


class FullConfigurationTest extends Specification {

    @Configuration
    @EnableJwtProcessing(mode = JwtProcessorMode.FULL)
    static class TestConfig implements JwtProcessorConfigurer<JwtProcessorBuilder> {

        @Bean
        SecretKey secretKey() {
            KeyGenerator.getInstance("HmacSHA256").generateKey()
        }

        @Override
        void configure(JwtProcessorBuilder builder) {
            builder.signAndVerifyWith(SignatureAlgorithms.HS256, secretKey())
        }
    }


    @SuppressWarnings("GroovyAssignabilityCheck")
    def "Spring application context should load"() {
        given:
            def applicationContext = new AnnotationConfigApplicationContext()
            applicationContext.register(TestConfig)

        when:
            applicationContext.refresh()

        then:
            applicationContext hasBeanOfType(JwtProcessor)
        and:
            applicationContext hasBeanOfType(JwtEncodingProcessor)
        and:
            applicationContext hasBeanOfType(JwtDecodingProcessor)
    }
}
