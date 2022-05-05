package com.thomasvitale.spring.cloudevents.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class CloudEventsRSocketAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CloudEventsRSocketAutoConfiguration.class, RSocketStrategiesAutoConfiguration.class));

    @Test
    void customizerConfiguredWithHighestPriority() {
        this.contextRunner.withUserConfiguration(UserConfiguration.class).run((context) -> {
            var provider = context.getBeanProvider(RSocketStrategiesCustomizer.class);
            assertThat(provider.orderedStream().count()).isGreaterThan(1);

            var cloudEventsCustomizer = provider.orderedStream().findFirst();
            assertThat(cloudEventsCustomizer).isPresent();
            assertThat(context).getBean("cloudEventsCustomizer").isSameAs(cloudEventsCustomizer.get());
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class UserConfiguration {

        @Bean
        RSocketStrategiesCustomizer myRSocketStrategiesCustomizer() {
            return strategies -> {
                strategies.encoder(new Jackson2JsonEncoder());
                strategies.decoder(new Jackson2JsonDecoder());
            };
        }

    }

}
