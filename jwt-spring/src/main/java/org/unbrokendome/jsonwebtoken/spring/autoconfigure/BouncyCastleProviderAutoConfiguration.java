package org.unbrokendome.jsonwebtoken.spring.autoconfigure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.security.Security;


@Configuration
@ConditionalOnClass(name = "org.bouncycastle.jce.provider.BouncyCastleProvider")
@AutoConfigureBefore({
        JwtAutoConfiguration.class
})
public class BouncyCastleProviderAutoConfiguration {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
}
