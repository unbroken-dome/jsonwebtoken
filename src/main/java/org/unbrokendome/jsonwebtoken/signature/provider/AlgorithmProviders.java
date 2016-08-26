package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import javax.crypto.Mac;
import java.security.Signature;
import java.util.Optional;


public final class AlgorithmProviders {

    public static AlgorithmProvider<Mac> mac(String algorithm, @Nullable PoolConfigurer poolConfigurer) {
        return poolConfigurer != null ? pooledMac(algorithm, poolConfigurer) : simpleMac(algorithm);
    }


    public static AlgorithmProvider<Mac> simpleMac(String algorithm) {
        return new MacAlgorithmProvider(algorithm);
    }


    public static AlgorithmProvider<Mac> pooledMac(String algorithm, PoolConfigurer poolConfigurer) {
        return new PooledMacAlgorithmProvider(algorithm, poolConfigurer);
    }


    public static AlgorithmProvider<Signature> rsa(String algorithm, @Nullable PoolConfigurer poolConfigurer) {
        return poolConfigurer != null ? pooledRsa(algorithm, poolConfigurer) : simpleRsa(algorithm);
    }


    public static AlgorithmProvider<Signature> simpleRsa(String algorithm) {
        return new RsaAlgorithmProvider(algorithm);
    }


    public static AlgorithmProvider<Signature> pooledRsa(String algorithm, PoolConfigurer poolConfigurer) {
        return new PooledRsaAlgorithmProvider(algorithm, poolConfigurer);
    }
}
