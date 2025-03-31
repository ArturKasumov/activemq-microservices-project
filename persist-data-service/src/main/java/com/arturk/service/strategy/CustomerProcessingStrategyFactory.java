package com.arturk.service.strategy;

import com.arturk.common.enums.MessageTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessingStrategyFactory {

    public CustomerProcessingStrategy getStrategy(String messageType) {
        if (MessageTypeEnum.FULLNAME_TYPE.getName().equals(messageType)) {
            return new FullNameProcessingStrategy();
        } else if (MessageTypeEnum.ADDRESS_TYPE.getName().equals(messageType)) {
            return new AddressProcessingStrategy();
        }
        throw new IllegalArgumentException("Not supported type");
    }
}
