package org.unbrokendome.jsonwebtoken.impl;

import java.util.Map;

import org.unbrokendome.jsonwebtoken.Claims;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DefaultClaims extends AbstractMapData implements Claims {

	@JsonCreator
	public DefaultClaims(Map<String, Object> map) {
		super(map);
	}
}
