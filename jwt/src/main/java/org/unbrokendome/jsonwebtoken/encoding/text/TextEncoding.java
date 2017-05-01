package org.unbrokendome.jsonwebtoken.encoding.text;

import org.unbrokendome.jsonwebtoken.BinaryData;

import java.util.function.Function;


public interface TextEncoding {

    Function<BinaryData, String> getEncoder();


    Function<String, BinaryData> getDecoder();
}
