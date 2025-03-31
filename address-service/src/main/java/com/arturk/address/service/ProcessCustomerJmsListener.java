package com.arturk.address.service;

import com.arturk.address.dto.AddressDto;
import com.arturk.common.enums.MessageTypeEnum;
import com.arturk.common.exception.JMSSendingException;
import com.arturk.common.exception.SerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessCustomerJmsListener {

    @Value("${activemq.persist-data-queue}")
    private String persistDataQueue;

    private final JmsTemplate jmsTemplate;

    @JmsListener(
            containerFactory = "jmsProcessCustomerListenerContainerFactory",
            subscription = "processCustomerSubscription",
            destination = "${activemq.process-customer-topic}"
    )
    public void processMessage(Message message) throws JMSException {
        Long customerId = Long.valueOf(((TextMessage) message).getText());
        AddressDto addressDto = new AddressDto("Ukraine", "Kharkiv");
        try {
            jmsTemplate.send(persistDataQueue, session -> createMessage(customerId, addressDto, message.getJMSCorrelationID(), session));
        } catch (Exception exception) {
            log.error("Error during sending jms message", exception);
            throw new JMSSendingException();
        }
    }

    private Message createMessage(Long customerId, AddressDto addressDto, String jmsCorrelationID, Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setLongProperty("customerId", customerId);
        message.setStringProperty("messageType", MessageTypeEnum.ADDRESS_TYPE.getName());
        try {
            message.setText(new ObjectMapper().writeValueAsString(addressDto));
        } catch (JsonProcessingException e) {
            log.error("Error during serializing object to message", e);
            throw new SerializationException();
        }
        message.setJMSCorrelationID(jmsCorrelationID);
        return message;
    }
}
