package com.arturk.common.exception;

public class SerializationException extends CustomTechnicalException {

    public SerializationException()  {
        super("CS-T-002", "Serialization object failed");
    }
}
