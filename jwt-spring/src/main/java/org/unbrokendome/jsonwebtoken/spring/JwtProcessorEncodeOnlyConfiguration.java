package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unbrokendome.jsonwebtoken.Jwt;
import org.unbrokendome.jsonwebtoken.JwtEncodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtEncodingProcessor;


@Configuration
public class JwtProcessorEncodeOnlyConfiguration
        extends AbstractJwtProcessorConfiguration<JwtEncodingProcessor, JwtEncodeOnlyProcessorBuilder> {

    @Bean
    public JwtEncodingProcessor jwtProcessor() {
        return jwtProcessorBean();
    }


    @Override
    protected JwtEncodeOnlyProcessorBuilder createBuilder() {
        return Jwt.processor().encodeOnly();
    }
}
