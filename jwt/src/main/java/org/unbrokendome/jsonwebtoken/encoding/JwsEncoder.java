package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nullable;


public interface JwsEncoder {

    String encode(BinaryData header, BinaryData payload, @Nullable BinaryData signature);
}
