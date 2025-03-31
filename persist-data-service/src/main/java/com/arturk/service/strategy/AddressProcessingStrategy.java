package com.arturk.service.strategy;

import com.arturk.dto.AddressDto;
import com.arturk.entity.CustomerEntity;

public class AddressProcessingStrategy implements CustomerProcessingStrategy {

    @Override
    public void processCustomer(CustomerEntity customer, Object dto) {
        AddressDto addressDto = (AddressDto) dto;
        customer.setCountry(addressDto.country());
        customer.setCity(addressDto.city());
    }
}
