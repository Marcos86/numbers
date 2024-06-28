package com.mdelamo.numbers.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidNumberOrderByBinaryException extends RuntimeException {

    private final String code;
    private final String message;

}
