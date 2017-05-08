package org.unbrokendome.jsonwebtoken.spring.autoconfigure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.security.Security;


@Configuration
@ConditionalOnClass(name = "org.bouncycastle.jce.provider.BouncyCastleProvider")
public class BouncyCastleProviderAutoConfiguration {

    public BouncyCastleProviderAutoConfiguration() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
}
