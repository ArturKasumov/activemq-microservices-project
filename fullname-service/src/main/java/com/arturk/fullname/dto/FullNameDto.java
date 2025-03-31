package com.arturk.fullname.dto;

import java.io.Serializable;

public record FullNameDto(String firstName, String lastName) implements Serializable {
}
