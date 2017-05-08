package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unbrokendome.jsonwebtoken.Jwt;
import org.unbrokendome.jsonwebtoken.JwtProcessor;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilder;


@Configuration
public class JwtProcessorFullConfiguration
        extends AbstractJwtProcessorConfiguration<JwtProcessor, JwtProcessorBuilder> {

    @Bean
    public JwtProcessor jwtProcessor() {
        return jwtProcessorBean();
    }


    @Override
    protected JwtProcessorBuilder createBuilder() {
        return Jwt.processor();
    }
}
