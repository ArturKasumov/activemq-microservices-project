package com.arturk.dto;

import java.io.Serializable;

public record AddressDto(String country, String city) implements Serializable {
}
