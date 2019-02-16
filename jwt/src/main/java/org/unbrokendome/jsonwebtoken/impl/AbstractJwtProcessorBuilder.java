package org.unbrokendome.jsonwebtoken.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.unbrokendome.jsonwebtoken.JwtProcessorBase;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilderBase;
import org.unbrokendome.jsonwebtoken.signature.provider.PoolConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;


abstract class AbstractJwtProcessorBuilder<T extends JwtProcessorBase, B extends JwtProcessorBuilderBase<T, B>>
        implements JwtProcessorBuilderBase<T, B> {

    private Supplier<ObjectMapper> objectMapperSupplier = ObjectMapper::new;
    private PoolConfigurer poolConfigurer;


    @Nonnull
    final ObjectMapper getObjectMapper() {
        return objectMapperSupplier.get();
    }


    @Nullable
    final PoolConfigurer getPoolConfigurer() {
        return poolConfigurer;
    }


    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public B setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapperSupplier = () -> objectMapper;
        return (B) this;
    }


    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public B configurePool(int minSize, int maxIdle) {
        this.poolConfigurer = settings -> settings.min(minSize).maxIdle(maxIdle);
        return (B) this;
    }
}
