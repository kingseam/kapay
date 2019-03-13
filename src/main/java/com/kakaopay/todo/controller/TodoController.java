package com.kakaopay.todo.controller;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.dto.ResponseTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TodoController {
    private final TodoService todoservice;

    @Autowired
    public TodoController(TodoService todoservice){
        this.todoservice = todoservice;
    }

    @GetMapping("/todos")
    public @ResponseBody
    ResponseTodoDto getAll(RequestTodoDto dto) {
        log.info("getAll()={{}",dto);
        return ResponseTodoDto.builder().result(todoservice.getAllTodo(dto)).build();
    }

    @GetMapping("/todos/{id}")
    public @ResponseBody
    ResponseTodoDto getTodoById(RequestTodoDto dto) {
        log.info("get={}", dto);
        return ResponseTodoDto.builder().result(todoservice.getTodoById(dto)).build();
    }

    @PutMapping("/todos/{id}")
    public @ResponseBody
    ResponseTodoDto modifyTodoById(@RequestBody RequestTodoDto dto) {
        log.info("modifyTodoById={}", dto);
        return ResponseTodoDto.builder().result(todoservice.updateTodo(dto)).build();
    }


    @PostMapping("/todos")
    public @ResponseBody
    ResponseTodoDto addTodo(@RequestBody RequestTodoDto dto) {
        log.info("addTodo={}", dto);
        return ResponseTodoDto.builder().result(todoservice.addTodo(dto)).build();
    }
}
