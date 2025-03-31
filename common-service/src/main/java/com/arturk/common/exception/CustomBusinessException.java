package com.arturk.common.exception;

public class CustomBusinessException extends CustomException {

    public CustomBusinessException(String code, String message) {
        super(code, message);
    }
}
