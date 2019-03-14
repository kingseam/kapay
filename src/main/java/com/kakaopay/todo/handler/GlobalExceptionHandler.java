package com.kakaopay.todo.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kakaopay.todo.dto.ResponseTodoDto;
import com.kakaopay.todo.exception.JsonParseException;
import com.kakaopay.todo.exception.ValidCustomException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MismatchedInputException.class)
    public ResponseTodoDto mismatchException(MismatchedInputException exception) {
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value())).result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = JsonParseException.class)
    public ResponseTodoDto jsonParseException(JsonParseException exception) {
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value())).result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = JdbcSQLException.class)
    public ResponseTodoDto jdbcSQLException(JdbcSQLException exception) {
        return ResponseTodoDto.builder().code("5001").result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseTodoDto sqlException(SQLException exception) {
        return ResponseTodoDto.builder().code("50000").result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseTodoDto bindException(BindException exception) {
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value())).result("Mismatched json format").build();
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseTodoDto noSuchElementException(NoSuchElementException exception) {
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.NO_CONTENT.value())).result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = ValidCustomException.class)
    public ResponseTodoDto validCustomException(ValidCustomException exception) {
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value())).result(exception.getMessage()).build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseTodoDto exception(Exception exception) {
        log.info("exception={}", exception);
        return ResponseTodoDto.builder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())).result(exception.getMessage()).build();
    }
}
