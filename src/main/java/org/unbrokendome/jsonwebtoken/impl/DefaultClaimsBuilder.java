package org.unbrokendome.jsonwebtoken.impl;

import org.unbrokendome.jsonwebtoken.Claims;
import org.unbrokendome.jsonwebtoken.ClaimsBuilder;

import javax.annotation.Nonnull;
import java.util.Map;


public class DefaultClaimsBuilder extends AbstractMapDataBuilder<ClaimsBuilder, Claims> implements ClaimsBuilder {

    @Override
    @Nonnull
    protected Claims buildFromMap(Map<String, Object> map) {
        return new DefaultClaims(map);
    }
}
