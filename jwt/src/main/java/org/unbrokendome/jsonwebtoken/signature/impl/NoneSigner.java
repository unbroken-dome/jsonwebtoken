package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.Signer;


public final class NoneSigner implements Signer<NoneKey> {

    private static final NoneSigner INSTANCE = new NoneSigner();


    private NoneSigner() {
    }


    public static NoneSigner getInstance() {
        return INSTANCE;
    }


    @Override
    public BinaryData sign(BinaryData header, BinaryData payload, NoneKey key) {
        return BinaryData.EMPTY;
    }
}
