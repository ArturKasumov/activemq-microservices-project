package com.arturk.common.enums;

import lombok.Getter;

@Getter
public enum MessageTypeEnum {

    ADDRESS_TYPE("address"),
    FULLNAME_TYPE("fullname");

    private final String name;

    MessageTypeEnum(String name) {
        this.name = name;
    }
}
