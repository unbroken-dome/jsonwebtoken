package org.unbrokendome.jsonwebtoken.spring;

public enum JwtProcessorMode {

    /**
     * The processor should support full JWT operations (both encoding and decoding).
     */
    FULL,

    /**
     * The processor should only support encoding of claims into JWTs, including cryptographic signing.
     */
    ENCODE_ONLY,

    /**
     * The processor should only support decoding of JWTs into claims,
     * including verification of cryptographic signatures.
     */
    DECODE_ONLY
}
