package org.unbrokendome.jsonwebtoken;

import javax.annotation.Nonnull;


public interface JoseHeaderBuilder extends MapDataBuilder<JoseHeaderBuilder, JoseHeader>, JoseHeader {

    /**
     * Sets the {@value JoseHeader#TYPE} entry for this header.
     *
     * @param type the value for the {@value JoseHeader#TYPE} entry
     * @return the current builder instance
     */
    @Nonnull
    default JoseHeaderBuilder setType(String type) {
        return set(TYPE, type);
    }


    /**
     * Sets the {@value JoseHeader#CONTENT_TYPE} entry for this header.
     *
     * @param contentType the value for the {@value JoseHeader#CONTENT_TYPE} entry
     * @return the current builder instance
     */
    @Nonnull
    default JoseHeaderBuilder setContentType(String contentType) {
        return set(CONTENT_TYPE, contentType);
    }


    /**
     * Sets the {@value JoseHeader#ALGORITHM} entry for this header.
     *
     * @param algorithm the value for the {@value JoseHeader#ALGORITHM} entry
     * @return the current builder instance
     */
    @Nonnull
    default JoseHeaderBuilder setAlgorithm(String algorithm) {
        return set(ALGORITHM, algorithm);
    }


    /**
     * Sets the {@value JoseHeader#KEY_ID} entry for this header.
     *
     * @param keyId the value for the {@value JoseHeader#KEY_ID} entry
     * @return the current builder instance
     */
    @Nonnull
    default JoseHeaderBuilder setKeyId(String keyId) {
        return set(KEY_ID, keyId);
    }
}
