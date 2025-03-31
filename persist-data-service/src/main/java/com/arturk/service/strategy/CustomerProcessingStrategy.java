package com.arturk.service.strategy;

import com.arturk.entity.CustomerEntity;

import java.io.Serializable;

public interface CustomerProcessingStrategy {
    void processCustomer(CustomerEntity customer, Object dto);
}
