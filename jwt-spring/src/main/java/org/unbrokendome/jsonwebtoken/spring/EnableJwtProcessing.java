package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JwtProcessorConfigurationImportSelector.class)
public @interface EnableJwtProcessing {

    @SuppressWarnings("unused") // is used via reflection in JwtProcessorConfigurationImportSelector
    JwtProcessorMode mode() default JwtProcessorMode.FULL;
}
