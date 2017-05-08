package org.unbrokendome.jsonwebtoken.signature.impl;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class RSASignatureAlgorithm extends AbstractAsymmetricSignatureAlgorithm {

    public RSASignatureAlgorithm(String jwaName, String jcaName, String keyFamily, @Nullable String jcaProvider) {
        super(jwaName, jcaName, keyFamily, jcaProvider);
    }


    public RSASignatureAlgorithm(String jwaName, String jcaName, String keyFamily) {
        super(jwaName, jcaName, keyFamily);
    }


    @Override
    protected PublicKeyExtractor getPublicKeyExtractor() {

        List<PublicKeyExtractor> extractors = new ArrayList<>();
        extractors.add(new JcaRSAPublicKeyExtractor());

        try {
            Class.forName("org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey");
            extractors.add(new BouncyCastleRsaPublicKeyExtractor());

        } catch (ClassNotFoundException ex) {
            // BouncyCastle is not on the classpath; skip this extractor
        }

        return new PublicKeyExtractorChain(extractors);
    }
}
