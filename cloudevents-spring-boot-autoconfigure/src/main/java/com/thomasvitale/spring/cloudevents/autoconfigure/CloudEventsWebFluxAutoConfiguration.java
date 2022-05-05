package com.thomasvitale.spring.cloudevents.autoconfigure;

import io.cloudevents.spring.webflux.CloudEventHttpMessageReader;
import io.cloudevents.spring.webflux.CloudEventHttpMessageWriter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
@AutoConfigureBefore(org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.class)
public class CloudEventsWebFluxAutoConfiguration {

    @Bean
    public CodecCustomizer cloudEventsCodecCustomizer() {
        return codecConfigurer -> {
            codecConfigurer.customCodecs().register(new CloudEventHttpMessageReader());
            codecConfigurer.customCodecs().register(new CloudEventHttpMessageWriter());
        };
    }

}
