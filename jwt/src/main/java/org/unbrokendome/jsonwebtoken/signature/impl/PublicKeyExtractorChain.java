package org.unbrokendome.jsonwebtoken.signature.impl;

import com.google.common.collect.ImmutableList;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;


public class PublicKeyExtractorChain implements PublicKeyExtractor {

    private final Collection<? extends PublicKeyExtractor> extractors;


    public PublicKeyExtractorChain(Iterable<? extends PublicKeyExtractor> extractors) {
        this.extractors = ImmutableList.copyOf(extractors);
    }


    @Override
    public boolean supports(PrivateKey privateKey) {
        return extractors.stream()
                .anyMatch(extractor -> extractor.supports(privateKey));
    }


    @Override
    public PublicKey publicKeyFromPrivateKey(PrivateKey privateKey, KeyFactory keyFactory)
            throws InvalidKeySpecException {
        for (PublicKeyExtractor extractor : extractors) {
            if (extractor.supports(privateKey)) {
                return extractor.publicKeyFromPrivateKey(privateKey, keyFactory);
            }
        }
        throw new IllegalArgumentException("Cannot construct public key from a private key of type "
                + privateKey.getClass().getName());
    }
}
