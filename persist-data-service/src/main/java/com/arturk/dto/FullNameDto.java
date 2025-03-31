package com.arturk.dto;

import java.io.Serializable;

public record FullNameDto(String firstName, String lastName) implements Serializable {
}
