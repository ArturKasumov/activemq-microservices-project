package com.arturk.common.exception;

public class JMSSendingException extends CustomTechnicalException {

    public JMSSendingException() {
        super("CS-T-001", "JMS sending failed");
    }

}
