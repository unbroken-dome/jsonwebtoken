package org.unbrokendome.jsonwebtoken.signature;

import org.unbrokendome.jsonwebtoken.signature.impl.NoneSignatureAlgorithm;

import java.security.Key;


/**
 * A dummy implementation of {@link Key} used by the {@link NoneSignatureAlgorithm}.
 */
public final class NoneKey implements Key {

    private static final NoneKey INSTANCE = new NoneKey();


    private NoneKey() {
    }


    /**
     * Gets the singleton instance of {@link NoneKey}.
     *
     * @return the singleton instance
     */
    public static NoneKey getInstance() {
        return INSTANCE;
    }


    @Override
    public String getAlgorithm() {
        return null;
    }


    @Override
    public String getFormat() {
        return null;
    }


    @Override
    public byte[] getEncoded() {
        return null;
    }
}
