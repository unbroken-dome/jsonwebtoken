package org.unbrokendome.jsonwebtoken.spring.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;
import org.unbrokendome.jsonwebtoken.spring.JwtProcessorConfigurer;
import org.unbrokendome.jsonwebtoken.spring.JwtProcessorDecodeOnlyConfiguration;
import org.unbrokendome.jsonwebtoken.spring.JwtProcessorEncodeOnlyConfiguration;
import org.unbrokendome.jsonwebtoken.spring.JwtProcessorFullConfiguration;
import org.unbrokendome.jsonwebtoken.spring.SignatureAlgorithmConverter;


@Configuration
@ConditionalOnMissingBean({ JwtEncodingProcessor.class, JwtDecodingProcessor.class })
public class JwtAutoConfiguration {

    @Bean
    @ConfigurationPropertiesBinding
    public SignatureAlgorithmConverter signatureAlgorithmConverter() {
        return new SignatureAlgorithmConverter();
    }


    @Configuration
    @ConditionalOnProperty(prefix = "jwt", name = "mode", havingValue = "FULL", matchIfMissing = true)
    @Import(JwtProcessorFullConfiguration.class)
    @EnableConfigurationProperties(JwtProperties.class)
    protected static class FullConfiguration
            implements JwtProcessorConfigurer<JwtProcessorBuilder> {

        @Autowired
        private JwtProperties jwtProperties;

        @Override
        public void configure(JwtProcessorBuilder builder) {
            jwtProperties.configure(builder);
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "jwt", name = "mode", havingValue = "ENCODE_ONLY")
    @Import(JwtProcessorEncodeOnlyConfiguration.class)
    @EnableConfigurationProperties(JwtProperties.class)
    protected static class EncodeOnlyConfiguration
            implements JwtProcessorConfigurer<JwtEncodeOnlyProcessorBuilder> {

        @Autowired
        private JwtProperties jwtProperties;

        @Override
        public void configure(JwtEncodeOnlyProcessorBuilder builder) {
            jwtProperties.configure(builder);
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "jwt", name = "mode", havingValue = "DECODE_ONLY")
    @Import(JwtProcessorDecodeOnlyConfiguration.class)
    @EnableConfigurationProperties(JwtProperties.class)
    protected static class DecodeOnlyConfiguration
            implements JwtProcessorConfigurer<JwtDecodeOnlyProcessorBuilder> {

        @Autowired
        private JwtProperties jwtProperties;

        @Override
        public void configure(JwtDecodeOnlyProcessorBuilder builder) {
            jwtProperties.configure(builder);
        }
    }
}
