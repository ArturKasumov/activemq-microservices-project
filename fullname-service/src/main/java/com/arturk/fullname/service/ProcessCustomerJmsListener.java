package com.arturk.fullname.service;

import com.arturk.common.enums.MessageTypeEnum;
import com.arturk.common.exception.JMSSendingException;
import com.arturk.common.exception.SerializationException;
import com.arturk.fullname.dto.FullNameDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
        FullNameDto fullNameDto = getRandomFullName();
        try {
            jmsTemplate.send(persistDataQueue, session -> createMessage(customerId, fullNameDto, message.getJMSCorrelationID(), session));
        } catch (Exception exception) {
            log.error("Error during sending jms message", exception);
            throw new JMSSendingException();
        }
    }

    private Message createMessage(Long customerId, FullNameDto fullNameDto, String jmsCorrelationID, Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setLongProperty("customerId", customerId);
        message.setStringProperty("messageType", MessageTypeEnum.FULLNAME_TYPE.getName());
        try {
            message.setText(new ObjectMapper().writeValueAsString(fullNameDto));
        } catch (JsonProcessingException e) {
            log.error("Error during serializing object to message", e);
            throw new SerializationException();
        }
        message.setJMSCorrelationID(jmsCorrelationID);
        return message;
    }

    private final static List<String> FIRST_NAMES = List.of("Alice", "Bob", "Charlie", "David", "Eve");
    private final static List<String> LAST_NAMES = List.of("Smith", "Johnson", "Brown", "Williams", "Jones");

    private FullNameDto getRandomFullName() {
        Random random = new Random();

        String firstName = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
        String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));

        return new FullNameDto(firstName, lastName);
    }
}
