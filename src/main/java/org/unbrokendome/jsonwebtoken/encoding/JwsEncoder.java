package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;


public interface JwsEncoder {

    String encode(BinaryData header, BinaryData payload, BinaryData signature);
}
