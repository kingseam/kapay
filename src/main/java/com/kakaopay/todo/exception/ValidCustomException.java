package com.kakaopay.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidCustomException extends RuntimeException {

    public ValidCustomException(String message) {
        super(message);
    }

    public ValidCustomException(String message, Exception exception) {
        super(message, exception);
    }
}
