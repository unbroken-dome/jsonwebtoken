package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unbrokendome.jsonwebtoken.Jwt;
import org.unbrokendome.jsonwebtoken.JwtDecodeOnlyProcessorBuilder;
import org.unbrokendome.jsonwebtoken.JwtDecodingProcessor;


@Configuration
public class JwtProcessorDecodeOnlyConfiguration
        extends AbstractJwtProcessorConfiguration<JwtDecodingProcessor, JwtDecodeOnlyProcessorBuilder> {

    @Bean
    public JwtDecodingProcessor jwtProcessor() {
        return jwtProcessorBean();
    }


    @Override
    protected JwtDecodeOnlyProcessorBuilder createBuilder() {
        return Jwt.processor().decodeOnly();
    }
}
