package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.NoneKey;
import org.unbrokendome.jsonwebtoken.signature.Signer;

import javax.annotation.Nonnull;


final class NoneSigner implements Signer<NoneKey> {

    private static final NoneSigner INSTANCE = new NoneSigner();


    private NoneSigner() {
    }


    static NoneSigner getInstance() {
        return INSTANCE;
    }


    @Nonnull
    @Override
    public BinaryData sign(BinaryData header, BinaryData payload, NoneKey key) {
        return BinaryData.EMPTY;
    }
}
