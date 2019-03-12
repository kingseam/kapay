package com.kakaopay.todo.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kakaopay.todo.dto.ResponseTodoDto;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MismatchedInputException.class)
    public ResponseTodoDto mismatchException(MismatchedInputException exception) {
        return ResponseTodoDto.builder().code("20000").result("input is mismatched").build();
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseTodoDto noSuchElementException(NoSuchElementException exception) {
        return ResponseTodoDto.builder().code("200").result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseTodoDto exception(Exception exception) {
        return ResponseTodoDto.builder().code("500").result(exception.getMessage()).build();
    }
}
