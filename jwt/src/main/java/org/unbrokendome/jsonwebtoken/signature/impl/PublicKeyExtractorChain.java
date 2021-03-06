package org.unbrokendome.jsonwebtoken.signature.impl;

import javax.annotation.Nonnull;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;


final class PublicKeyExtractorChain implements PublicKeyExtractor {

    private final List<? extends PublicKeyExtractor> extractors;


    PublicKeyExtractorChain(List<? extends PublicKeyExtractor> extractors) {
        this.extractors = extractors;
    }


    @Override
    public boolean supports(PrivateKey privateKey) {
        return extractors.stream()
                .anyMatch(extractor -> extractor.supports(privateKey));
    }


    @Nonnull
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
