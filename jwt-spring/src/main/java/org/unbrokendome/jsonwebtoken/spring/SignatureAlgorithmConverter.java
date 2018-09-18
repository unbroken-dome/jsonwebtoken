package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.core.convert.converter.Converter;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithm;
import org.unbrokendome.jsonwebtoken.signature.SignatureAlgorithms;

import javax.annotation.Nullable;


public class SignatureAlgorithmConverter implements Converter<String, SignatureAlgorithm<?, ?>> {

    @Override
    @Nullable
    public SignatureAlgorithm<?, ?> convert(@Nullable String source) {
        if (source == null) {
            return null;
        }
        return SignatureAlgorithms.get(source.toUpperCase());
    }
}
