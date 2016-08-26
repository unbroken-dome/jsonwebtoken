package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import javax.crypto.Mac;


public final class PooledMacAlgorithmProvider extends AbstractPooledAlgorithmProvider<Mac> {

    public PooledMacAlgorithmProvider(String algorithm, @Nullable String provider, PoolConfigurer poolConfigurer) {
        super(new MacPoolableObject(algorithm, provider), poolConfigurer);
    }
}
