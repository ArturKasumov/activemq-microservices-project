package com.arturk.service;

import com.arturk.common.enums.MessageTypeEnum;
import com.arturk.dto.AddressDto;
import com.arturk.dto.FullNameDto;
import com.arturk.entity.CustomerEntity;
import com.arturk.entity.repository.CustomerRepository;
import com.arturk.service.strategy.AddressProcessingStrategy;
import com.arturk.service.strategy.CustomerProcessingStrategy;
import com.arturk.service.strategy.CustomerProcessingStrategyFactory;
import com.arturk.service.strategy.FullNameProcessingStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersistCustomerJmsListener {

    private final CustomerProcessingStrategyFactory customerProcessingStrategyFactory;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @JmsListener(
            containerFactory = "jmsPersistCustomerListenerContainerFactory",
            subscription = "processPersistCustomerSubscription",
            destination = "${activemq.persist-data-queue}"
    )
    public void processMessage(TextMessage message) throws JMSException, JsonProcessingException {
        Long customerId = message.getLongProperty("customerId");
        String messageType = message.getStringProperty("messageType");
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElse(null);
        if (customerEntity == null) {
            customerEntity = new CustomerEntity();
            customerEntity.setId(customerId);
        }
        CustomerProcessingStrategy strategy = customerProcessingStrategyFactory.getStrategy(messageType);
        strategy.processCustomer(customerEntity, objectMapper.readValue(message.getText(), getClassBasedOnMessageType(messageType)) );
        customerRepository.save(customerEntity);
    }

    private Class<?> getClassBasedOnMessageType(String messageType) {
        if (MessageTypeEnum.FULLNAME_TYPE.getName().equals(messageType)) {
            return FullNameDto.class;
        } else if (MessageTypeEnum.ADDRESS_TYPE.getName().equals(messageType)) {
            return AddressDto.class;
        }
        throw new IllegalArgumentException("Not supported type");
    }
}
