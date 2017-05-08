package org.unbrokendome.jsonwebtoken.signature.impl;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class EllipticCurveSignatureAlgorithm
        extends AbstractAsymmetricSignatureAlgorithm {

    private static final String KEY_FAMILY = "EC";

    // BouncyCastle is the preferred provider, it will be used if installed
    private static final String PREFERRED_PROVIDER = "BC";


    public EllipticCurveSignatureAlgorithm(String jwaName, String jcaName, @Nullable String jcaProvider) {
        super(jwaName, jcaName, KEY_FAMILY, jcaProvider);
    }


    public EllipticCurveSignatureAlgorithm(String jwaName, String jcaName) {
        super(jwaName, jcaName, KEY_FAMILY);
    }


    @Nullable
    @Override
    protected String getPreferredProvider() {
        return PREFERRED_PROVIDER;
    }


    @Override
    protected PublicKeyExtractor getPublicKeyExtractor() {

        List<PublicKeyExtractor> extractors = new ArrayList<>();

        try {
            Class.forName("org.bouncycastle.jce.interfaces.ECPrivateKey");
            extractors.add(new BouncyCastleECPublicKeyExtractor());

        } catch (ClassNotFoundException ex) {
            // BouncyCastle is not on the classpath; skip this extractor
        }

        return new PublicKeyExtractorChain(extractors);
    }
}
