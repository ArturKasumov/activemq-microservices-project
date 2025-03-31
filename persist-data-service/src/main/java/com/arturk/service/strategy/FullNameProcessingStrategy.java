package com.arturk.service.strategy;

import com.arturk.dto.FullNameDto;
import com.arturk.entity.CustomerEntity;

public class FullNameProcessingStrategy implements CustomerProcessingStrategy {

    @Override
    public void processCustomer(CustomerEntity customer, Object dto) {
        FullNameDto fullNameDto = (FullNameDto) dto;
        customer.setFirstName(fullNameDto.firstName());
        customer.setLastName(fullNameDto.lastName());
    }
}
