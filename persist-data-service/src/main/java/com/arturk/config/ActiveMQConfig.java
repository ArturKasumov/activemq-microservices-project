package com.arturk.config;

import jakarta.jms.ConnectionFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Arrays;

@Configuration
@EnableJms
@EnableConfigurationProperties(ActiveMQConfig.ActiveMQProperties.class)
@RequiredArgsConstructor
public class ActiveMQConfig {

    private final ActiveMQProperties properties;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(properties.getBrokerUrl());
        connectionFactory.setUserName(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        connectionFactory.setClientID(properties.getClientId());
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsPersistCustomerListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setClientId(properties.getClientId());
        return factory;
    }

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "activemq")
    public static class ActiveMQProperties {
        private String brokerUrl;
        private String username;
        private String password;
        private String clientId;
    }
}
