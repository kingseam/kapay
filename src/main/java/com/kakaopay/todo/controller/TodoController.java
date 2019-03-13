package com.kakaopay.todo.controller;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.dto.ResponseTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return ResponseTodoDto.builder().result(todoservice.getAllTodo(dto)).build();
    }

    @GetMapping("/todos/{id}")
    public @ResponseBody
    ResponseTodoDto getTodoById(RequestTodoDto dto) {
        return ResponseTodoDto.builder().result(todoservice.getTodoById(dto)).build();
    }

    @PutMapping("/todos/{id}")
    public @ResponseBody
    ResponseTodoDto modifyTodoById(@RequestBody RequestTodoDto dto) {
        // vaildation check
        if(dto.getId() == null){
            throw new ValidCustomException("Required value (id) = {\"id\":\"1\", \"contents\":\"sample\"}");
        }
        return ResponseTodoDto.builder().result(todoservice.updateTodo(dto)).build();
    }

    @PostMapping("/todos")
    public @ResponseBody
    ResponseTodoDto addTodo(@RequestBody RequestTodoDto dto) {
        if(!StringUtils.equals("Y",dto.getStatusType())){
            dto.setStatusType("N");
        }
        return ResponseTodoDto.builder().result(todoservice.addTodo(dto)).build();
    }
}
