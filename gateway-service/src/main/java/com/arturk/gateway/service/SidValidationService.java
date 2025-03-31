package com.arturk.gateway.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SidValidationService {

    private static final Set<String> VALID_SIDS = Set.of("12345", "98765");

    public boolean isValid(String sid) {
        return VALID_SIDS.contains(sid);
    }
}
