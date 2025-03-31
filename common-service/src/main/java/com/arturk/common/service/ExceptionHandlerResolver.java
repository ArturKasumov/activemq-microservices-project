package com.arturk.common.service;

import com.arturk.common.exception.ErrorResponse;
import com.arturk.common.exception.CustomBusinessException;
import com.arturk.common.exception.CustomTechnicalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerResolver {

    private final static String COMMON_CODE = "CS-T-000";

    @ExceptionHandler(CustomBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleGatewayServiceBusinessException(CustomBusinessException exception, HttpServletRequest httpRequest) {
        log.error("handleGatewayServiceBusinessException", exception);
        ErrorResponse error = new ErrorResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomTechnicalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGatewayServiceTechnicalException(CustomTechnicalException exception, HttpServletRequest httpRequest) {
        log.error("handleGatewayServiceTechnicalException", exception);
        ErrorResponse error = new ErrorResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest httpRequest) {
        log.error("handleException", exception);
        ErrorResponse error = new ErrorResponse(COMMON_CODE, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
