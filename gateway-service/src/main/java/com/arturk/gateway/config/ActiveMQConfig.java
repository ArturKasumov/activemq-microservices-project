package com.arturk.gateway.config;

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
import org.springframework.jms.core.JmsTemplate;

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
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public ActiveMQTopic processCustomerTopic() {
        return new ActiveMQTopic(properties.getProcessCustomerTopic());
    }

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "activemq")
    public static class ActiveMQProperties {
        private String brokerUrl;
        private String username;
        private String password;
        private String clientId;
        private String processCustomerTopic;
    }
}
