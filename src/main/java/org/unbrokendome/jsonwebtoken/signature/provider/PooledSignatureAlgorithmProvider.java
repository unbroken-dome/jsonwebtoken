package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import java.security.Signature;


public class PooledSignatureAlgorithmProvider extends AbstractPooledAlgorithmProvider<Signature> {

    public PooledSignatureAlgorithmProvider(String algorithm, @Nullable String provider,
                                            PoolConfigurer poolConfigurer) {
        super(new SignaturePoolableObject(algorithm, provider), poolConfigurer);
    }
}
