package org.unbrokendome.jsonwebtoken.spring.autoconfigure

import groovy.json.JsonSlurper
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.After
import org.junit.Before
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.annotation.Order
import org.unbrokendome.jsonwebtoken.Jwt
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor
import org.unbrokendome.jsonwebtoken.JwtProcessor
import spock.lang.Specification

import java.security.Security

import static org.hamcrest.Matchers.not
import static org.unbrokendome.jsonwebtoken.spring.SpringMatchers.hasBeanOfType


@SuppressWarnings("GroovyAssignabilityCheck")
class JwtAutoConfigurationTest extends Specification {

    private StringWriter encoderOutputWriter
    private StringWriter decoderOutputWriter
    private ConfigurableApplicationContext outputContext


    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestConfig { }


    @Before
    void createOutputContext() {

        encoderOutputWriter = new StringWriter()
        decoderOutputWriter = new StringWriter()

        def beanFactory = new DefaultListableBeanFactory()
        beanFactory.registerSingleton('encoderOutput', new PrintWriter(encoderOutputWriter))
        beanFactory.registerSingleton('decoderOutput', new PrintWriter(decoderOutputWriter))

        outputContext = new GenericApplicationContext(beanFactory)
        outputContext.refresh()
    }


    @After
    void closeOutputContext() {
        outputContext.close()
    }


    @Configuration
    static class EncoderApplication {
        @Bean @Order(1)
        ApplicationRunner encoderRunner(JwtEncodingProcessor jwtProcessor,
                                        @Qualifier('encoderOutput') PrintWriter output) {
            return { args ->
                def claims = Jwt.claims().with {
                    subject = 'Till'
                    build()
                }
                def token = jwtProcessor.encode(claims)
                output.print(token)
            }
        }
    }


    @Configuration
    static class DecoderApplication {
        @Bean @Order(2)
        ApplicationRunner decoderRunner(JwtDecodingProcessor jwtProcessor,
                                        @Qualifier('decoderOutput') PrintWriter output) {
            return { args ->
                def token = args.getNonOptionArgs().first()
                def claims = jwtProcessor.decodeClaims(token)
                output.print(claims.subject)
            }
        }
    }


    private SpringApplicationBuilder springApplication() {
        new SpringApplicationBuilder(TestConfig)
                .parent(outputContext)
                .web(false)
    }


    def "Auto-configuration without customization"() {
        given:
            def application = springApplication()
        when:
            def applicationContext = application.run()
        then:
            applicationContext hasBeanOfType(JwtProcessor)
    }


    def "Auto-configuration with full mode"() {
        given:
            def application = springApplication()
                    .properties('jwt.mode': 'FULL')
        when:
            def applicationContext = application.run()
        then:
            applicationContext hasBeanOfType(JwtProcessor)
    }


    def "Auto-configuration with encode-only mode"() {
        given:
            def application = springApplication()
                    .properties('jwt.mode': 'ENCODE_ONLY')
        when:
            def applicationContext = application.run()
        then:
            applicationContext hasBeanOfType(JwtEncodingProcessor)
        and:
            applicationContext not(hasBeanOfType(JwtDecodingProcessor))
    }


    def "Auto-configuration with decode-only mode"() {
        given:
            def application = springApplication()
                    .properties('jwt.mode': 'DECODE_ONLY')
        when:
            def applicationContext = application.run()
        then:
            applicationContext hasBeanOfType(JwtDecodingProcessor)
        and:
            applicationContext not(hasBeanOfType(JwtEncodingProcessor))
    }


    def "Auto-configuration with HS256 signing"() {
        given:
            def application = springApplication()
                    .properties('jwt.signing.algorithm': 'HS256',
                                'jwt.signing.key': 'QYeVQ76sCMPsJ0eyn/MSYlA5ccpR/Bdu')
                    .sources(TestConfig, EncoderApplication)
        when:
            application.run()
        then:
            getAlgIdFromToken(encoderOutputText) == 'HS256'
    }


    def "Auto-configuration with ES256 signing"() {
        given:
            def pkcs8PrivateKey = '''
                MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgcs7rrcFpX9+iCjxg
                vWTQpszK2dnkfUTcjiQuvKvqx3ihRANCAASsnmspsrbYquQpYXVuiPpuGrNFVv/f
                iOur68LFPCv+JQ+6xHHMXuTAko1I6yZyCadHBAZuqKKnhKVl1EWGJV74
            '''.stripIndent().trim()

            def application = springApplication()
                    .properties('jwt.mode': 'ENCODE_ONLY',
                                'jwt.signing.algorithm': 'ES256',
                                'jwt.signing.key': pkcs8PrivateKey)
                    .sources(TestConfig, EncoderApplication)
        when:
            application.run()
        then:
            getAlgIdFromToken(encoderOutputText) == 'ES256'
    }


    def "Auto-configuration with ES256 verification"() {
        given:
            def x509PublicKey = '''
                MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErJ5rKbK22KrkKWF1boj6bhqzRVb/
                34jrq+vCxTwr/iUPusRxzF7kwJKNSOsmcgmnRwQGbqiip4SlZdRFhiVe+A==
            '''.stripIndent().trim()

            def tokenToDecode = 'eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJUaWxsIn0=.MEUCIQC64INn332LcuuUrONZMvfcyaOXKan5Y-j3dSnQ7DbJDQIgcfluPYsS9dIutblQen5kpk83_adwDKMb9OedaznKoYM='

            def application = springApplication()
                    .properties('jwt.mode': 'DECODE_ONLY',
                    'jwt.verification[0].algorithm': 'ES256',
                    'jwt.verification[0].key': x509PublicKey)
                    .sources(TestConfig, DecoderApplication)
        when:
            application.run(tokenToDecode)
        then:
            decoderOutputText == 'Till'
    }



    def "Auto-configuration with ES256 signing and verification and BouncyCastle provider"() {
        given:
            def bcProvider = new BouncyCastleProvider()
            Security.addProvider(bcProvider)

            def pkcs8PrivateKey = '''
                MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgcs7rrcFpX9+iCjxg
                vWTQpszK2dnkfUTcjiQuvKvqx3ihRANCAASsnmspsrbYquQpYXVuiPpuGrNFVv/f
                iOur68LFPCv+JQ+6xHHMXuTAko1I6yZyCadHBAZuqKKnhKVl1EWGJV74
            '''.stripIndent().trim()

            def tokenToDecode = 'eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJUaWxsIn0=.MEUCIQC64INn332LcuuUrONZMvfcyaOXKan5Y-j3dSnQ7DbJDQIgcfluPYsS9dIutblQen5kpk83_adwDKMb9OedaznKoYM='

            def application = springApplication()
                    .properties('jwt.mode': 'FULL',
                    'jwt.signing.algorithm': 'ES256',
                    //'jwt.signing.provider': 'BC',
                    'jwt.signing.key': pkcs8PrivateKey)
                    .sources(TestConfig, EncoderApplication, DecoderApplication)
        when:
            application.run(tokenToDecode)
        then:
            getAlgIdFromToken(encoderOutputText) == 'ES256'
        and:
            decoderOutputText == 'Till'

        cleanup:
            Security.removeProvider(bcProvider.getName())
    }


    private String getAlgIdFromToken(String token) {
        if (token) {
            def encodedHeader = token.split('\\.', 3).first()
            def jsonHeader = Base64.decoder.decode(encodedHeader)
            def header = new JsonSlurper().parse(jsonHeader)
            return header['alg']
        } else {
            null
        }
    }


    private String getEncoderOutputText() {
        encoderOutputWriter.toString()
    }


    private String getDecoderOutputText() {
        decoderOutputWriter.toString()
    }
}
