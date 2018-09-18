package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Map;


public class JwtProcessorConfigurationImportSelector implements ImportSelector {

    @Override
    @Nonnull
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        Map<String, Object> attributesMap = importingClassMetadata.getAnnotationAttributes(
                EnableJwtProcessing.class.getName(), false);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(attributesMap);

        Assert.notNull(attributes, String.format(
                "@%s is not present on importing class '%s' as expected",
                EnableJwtProcessing.class.getSimpleName(), importingClassMetadata.getClassName()));

        JwtProcessorMode mode = attributes.getEnum("mode");
        Class<?> configurationClassToImport = getConfigurationClassFromMode(mode);

        return new String[] { configurationClassToImport.getName() };
    }


    @Nonnull
    private Class<?> getConfigurationClassFromMode(JwtProcessorMode mode) {
        switch (mode) {
            case FULL:
                return JwtProcessorFullConfiguration.class;
            case ENCODE_ONLY:
                return JwtProcessorEncodeOnlyConfiguration.class;
            case DECODE_ONLY:
                return JwtProcessorDecodeOnlyConfiguration.class;
            default:
                throw new IllegalArgumentException("Unexpected value " + mode + " for JwtProcessorMode");
        }
    }
}
