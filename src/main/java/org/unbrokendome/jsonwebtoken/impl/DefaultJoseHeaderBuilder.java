package org.unbrokendome.jsonwebtoken.impl;

import java.util.Map;

import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.JoseHeaderBuilder;

public class DefaultJoseHeaderBuilder extends AbstractMapDataBuilder<JoseHeaderBuilder, JoseHeader> implements
		JoseHeaderBuilder {

	@Override
	protected JoseHeader buildFromMap(Map<String, Object> map) {
		return new DefaultJoseHeader(map);
	}
}
