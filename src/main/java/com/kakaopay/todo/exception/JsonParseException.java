package com.kakaopay.todo.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class JsonParseException extends RuntimeException {

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Exception exception) {
        super(message, exception);
    }
}
