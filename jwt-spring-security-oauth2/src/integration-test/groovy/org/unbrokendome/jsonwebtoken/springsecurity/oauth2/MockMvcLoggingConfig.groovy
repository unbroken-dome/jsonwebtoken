package org.unbrokendome.jsonwebtoken.springsecurity.oauth2

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.servlet.result.MockMvcResultHandlers


@Configuration
class MockMvcLoggingConfig {

    @Bean
    MockMvcBuilderCustomizer mockMvcBuilderCustomizer() {
        return { builder ->
            builder.alwaysDo(MockMvcResultHandlers.log())
        }
    }
}
