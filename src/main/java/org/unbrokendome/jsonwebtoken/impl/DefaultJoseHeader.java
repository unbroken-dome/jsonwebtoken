package org.unbrokendome.jsonwebtoken.impl;

import java.util.Map;

import org.unbrokendome.jsonwebtoken.JoseHeader;

public class DefaultJoseHeader extends AbstractMapData implements JoseHeader {

	public DefaultJoseHeader(Map<String, Object> map) {
		super(map);
	}
}
