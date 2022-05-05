package com.thomasvitale.spring.cloudevents.autoconfigure;

import io.cloudevents.spring.messaging.CloudEventMessageConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class CloudEventsMessagingAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CloudEventsMessagingAutoConfiguration.class));

    @Test
    void defaultConverterConfigured() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(CloudEventMessageConverter.class);
        });
    }

    @Test
    void defaultConverterBacksOff() {
        this.contextRunner.withUserConfiguration(UserConfiguration.class).run((context) -> {
            assertThat(context).hasSingleBean(CloudEventMessageConverter.class);
            assertThat(context).getBean("myCloudEventMessageConverter")
                    .isSameAs(context.getBean(CloudEventMessageConverter.class));
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class UserConfiguration {

        @Bean
        CloudEventMessageConverter myCloudEventMessageConverter() {
            return new CloudEventMessageConverter();
        }

    }

}
