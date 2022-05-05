package com.thomasvitale.spring.cloudevents.autoconfigure;

import io.cloudevents.spring.messaging.CloudEventMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MessageConverter.class)
public class CloudEventsMessagingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CloudEventMessageConverter cloudEventMessageConverter() {
        return new CloudEventMessageConverter();
    }

}
