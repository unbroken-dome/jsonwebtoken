package org.unbrokendome.jsonwebtoken.signature.provider;

import nf.fr.eraasoft.pool.PoolSettings;


public interface PoolConfigurer {

    void configure(PoolSettings<?> settings);
}
