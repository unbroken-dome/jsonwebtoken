package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.JwtProcessorBase;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilderBase;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import java.util.function.Supplier;


public abstract class AbstractJwtProcessorBuilder<T extends JwtProcessorBase, B extends JwtProcessorBuilderBase<T, B>>
        implements JwtProcessorBuilderBase<T, B> {

    private Supplier<ObjectMapper> objectMapperSupplier = ObjectMapper::new;
    private PoolConfigurer poolConfigurer;


    protected final ObjectMapper getObjectMapper() {
        return objectMapperSupplier.get();
    }


    protected final PoolConfigurer getPoolConfigurer() {
        return poolConfigurer;
    }


    @Override
    @SuppressWarnings("unchecked")
    public B setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapperSupplier = () -> objectMapper;
        return (B) this;
    }


    @Override
    @SuppressWarnings("unchecked")
    public B configurePool(int minSize, int maxIdle) {
        this.poolConfigurer = settings -> settings.min(minSize).maxIdle(maxIdle);
        return (B) this;
    }
}
