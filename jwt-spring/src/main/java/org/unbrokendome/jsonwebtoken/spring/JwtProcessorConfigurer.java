package org.unbrokendome.jsonwebtoken.spring;

import org.unbrokendome.jsonwebtoken.JwtProcessorBuilderBase;


public interface JwtProcessorConfigurer<B extends JwtProcessorBuilderBase<?, B>> {

    void configure(B builder);
}
