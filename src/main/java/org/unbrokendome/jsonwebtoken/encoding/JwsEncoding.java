package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;
import org.unbrokendome.jsonwebtoken.Jws;


public interface JwsEncoding {

    HeaderSerializer getHeaderSerializer();


    HeaderDeserializer getHeaderDeserializer();


    JwsEncoder getEncoder();


    JwsDecoder getDecoder();


    default BinaryData serializeHeader(JoseHeader header) {
        return getHeaderSerializer().serialize(header);
    }


    default JoseHeader deserializeHeader(BinaryData data) throws JwtMalformedTokenException {
        return getHeaderDeserializer().deserialize(data);
    }


    default String encode(BinaryData header, BinaryData payload, BinaryData signature) {
        return getEncoder().encode(header, payload, signature);
    }


    default Jws decode(String encoded) throws JwtMalformedTokenException {
        return getDecoder().decode(encoded);
    }
}
