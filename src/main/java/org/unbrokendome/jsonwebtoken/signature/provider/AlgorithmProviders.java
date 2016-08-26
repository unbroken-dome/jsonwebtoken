package org.unbrokendome.jsonwebtoken.signature.provider;

import javax.annotation.Nullable;
import javax.crypto.Mac;
import java.security.Signature;


public final class AlgorithmProviders {

    public static AlgorithmProvider<Mac> mac(String algorithm,
                                             @Nullable PoolConfigurer poolConfigurer) {
        return mac(algorithm, null, poolConfigurer);
    }


    public static AlgorithmProvider<Mac> mac(String algorithm, @Nullable String provider,
                                             @Nullable PoolConfigurer poolConfigurer) {
        return poolConfigurer != null ? pooledMac(algorithm, provider, poolConfigurer) : simpleMac(algorithm, provider);
    }


    public static AlgorithmProvider<Mac> simpleMac(String algorithm, @Nullable String provider) {
        return new MacAlgorithmProvider(algorithm, provider);
    }


    public static AlgorithmProvider<Mac> pooledMac(String algorithm, @Nullable String provider,
                                                   PoolConfigurer poolConfigurer) {
        return new PooledMacAlgorithmProvider(algorithm, provider, poolConfigurer);
    }


    public static AlgorithmProvider<Signature> signature(String algorithm, @Nullable String provider,
                                                         @Nullable PoolConfigurer poolConfigurer) {
        return poolConfigurer != null ?
                pooledSignature(algorithm, provider, poolConfigurer) :
                simpleSignature(algorithm, provider);
    }


    public static AlgorithmProvider<Signature> signature(String algorithm,
                                                         @Nullable PoolConfigurer poolConfigurer) {
        return signature(algorithm, null, poolConfigurer);
    }


    public static AlgorithmProvider<Signature> simpleSignature(String algorithm, @Nullable String provider) {
        return new SignatureAlgorithmProvider(algorithm, provider);
    }


    public static AlgorithmProvider<Signature> pooledSignature(String algorithm, @Nullable String provider,
                                                               PoolConfigurer poolConfigurer) {
        return new PooledSignatureAlgorithmProvider(algorithm, provider, poolConfigurer);
    }
}
