package org.unbrokendome.jsonwebtoken.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.unbrokendome.jsonwebtoken.JwtProcessorBase;
import org.unbrokendome.jsonwebtoken.JwtProcessorBuilderBase;

import javax.annotation.Nullable;
import java.util.List;


public abstract class AbstractJwtProcessorConfiguration<T extends JwtProcessorBase, B extends JwtProcessorBuilderBase<T, B>> {

    @Autowired(required = false)
    @Nullable
    private List<JwtProcessorConfigurer<B>> configurers;


    T jwtProcessorBean() {
        B builder = createBuilder();
        if (configurers != null) {
            for (JwtProcessorConfigurer<B> configurer : configurers) {
                configurer.configure(builder);
            }
        }
        return builder.build();
    }


    protected abstract B createBuilder();
}
