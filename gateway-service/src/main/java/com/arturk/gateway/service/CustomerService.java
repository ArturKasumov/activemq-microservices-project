package com.arturk.gateway.service;

import com.arturk.common.exception.JMSSendingException;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final JmsTemplate jmsTemplate;
    private final ActiveMQTopic processCustomerTopic;

    public void processCustomerData(Long customerId) {
        log.debug("Starting processing data for customer with id: {}", customerId);
        try {
            jmsTemplate.send(processCustomerTopic, session -> createMessage(customerId, session));
        } catch (Exception exception) {
            log.error("Error during sending jms message", exception);
            throw new JMSSendingException();
        }
    }

    private Message createMessage(Long customerId, Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setJMSCorrelationID(UUID.randomUUID().toString());
        message.setText(String.valueOf(customerId));
        return message;
    }
}
