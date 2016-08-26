package org.unbrokendome.jsonwebtoken.signature.impl;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.signature.JwsSignatureException;
import org.unbrokendome.jsonwebtoken.signature.Signer;

import java.security.Key;


public final class NoneSigner implements Signer {

    private static final NoneSigner INSTANCE = new NoneSigner();


    private NoneSigner() {
    }


    public static NoneSigner getInstance() {
        return INSTANCE;
    }


    @Override
    public BinaryData sign(BinaryData header, BinaryData payload, Key key) throws JwsSignatureException {
        return BinaryData.EMPTY;
    }
}
