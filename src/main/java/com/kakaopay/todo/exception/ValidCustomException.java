package com.kakaopay.todo.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ValidCustomException extends RuntimeException{
    private final String message;

    public ValidCustomException(String message){
        this.message = message;
    }
}
