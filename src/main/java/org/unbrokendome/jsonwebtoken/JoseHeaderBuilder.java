package org.unbrokendome.jsonwebtoken;

public interface JoseHeaderBuilder extends MapDataBuilder<JoseHeaderBuilder, JoseHeader>, JoseHeader {

    default JoseHeaderBuilder setType(String type) {
        return set(TYPE, type);
    }


    default JoseHeaderBuilder setContentType(String contentType) {
        return set(CONTENT_TYPE, contentType);
    }


    default JoseHeaderBuilder setAlgorithm(String algorithm) {
        return set(ALGORITHM, algorithm);
    }


    default JoseHeaderBuilder setKeyId(String keyId) {
        return set(KEY_ID, keyId);
    }
}
