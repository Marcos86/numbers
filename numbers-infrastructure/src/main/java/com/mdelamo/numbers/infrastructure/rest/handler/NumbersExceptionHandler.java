package com.mdelamo.numbers.infrastructure.rest.handler;

import com.mdelamo.numbers.domain.exception.InvalidNumberOrderByBinaryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class NumbersExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String ERROR_KEY = "error";
    private static final String CODE_KEY = "code";
    private static final String MESSAGE_KEY = "message";
    private static final String UNEXPECTED_CODE_ERROR = "UCE";


    @ExceptionHandler(InvalidNumberOrderByBinaryException.class)
    public ResponseEntity<Object> handleInvalidNumberOrderByBinaryException(final InvalidNumberOrderByBinaryException invalidNumberOrderByBinaryException, WebRequest request) {
        return createResponseEntity(invalidNumberOrderByBinaryException.getCode(), invalidNumberOrderByBinaryException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException runtimeException, final WebRequest request) {
        return createResponseEntity(UNEXPECTED_CODE_ERROR, runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createResponseEntity(final String code, String message, final HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(ERROR_KEY, buildErrorMap(code, message));
        return new ResponseEntity<>(body, httpStatus);
    }

    private Map<String, String> buildErrorMap(final String code, final String message) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put(CODE_KEY, code);
        error.put(MESSAGE_KEY, message);
        return error;
    }

}