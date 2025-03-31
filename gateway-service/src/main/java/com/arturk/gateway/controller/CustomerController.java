package com.arturk.gateway.controller;

import com.arturk.gateway.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v4/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(
            value = "/{customerId}",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> processCustomerData(@PathVariable Long customerId) {
        customerService.processCustomerData(customerId);
        return ResponseEntity.ok().build();
    }
}
