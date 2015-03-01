package org.unbrokendome.jsonwebtoken.impl;

import java.util.Map;

import org.unbrokendome.jsonwebtoken.Claims;
import org.unbrokendome.jsonwebtoken.ClaimsBuilder;

public class DefaultClaimsBuilder extends AbstractMapDataBuilder<ClaimsBuilder, Claims> implements ClaimsBuilder {

	@Override
	protected Claims buildFromMap(Map<String, Object> map) {
		return new DefaultClaims(map);
	}
}
