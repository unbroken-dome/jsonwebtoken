package org.unbrokendome.jsonwebtoken.encoding.text;

import java.util.function.Function;

import org.unbrokendome.jsonwebtoken.BinaryData;

public interface TextEncoding {

	Function<BinaryData, String> getEncoder();


	Function<String, BinaryData> getDecoder();
}
