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

    @GetMapping("/todolist")
    public @ResponseBody
    ResponseTodoDto getAll(RequestTodoDto dto) {
        log.info("getAll()={{}",dto);
        return ResponseTodoDto.builder().result(todoservice.getAllTodo(dto)).build();
    }

    @GetMapping("/todolist/{id}")
    public @ResponseBody
    ResponseTodoDto getTodoById(@PathVariable("id") Long id, RequestTodoDto dto) {
        log.info("get={}", dto);
        dto.setId(id);
        return ResponseTodoDto.builder().result(todoservice.getTodoById(dto)).build();
    }

    @PutMapping("/todolist/{id}")
    public @ResponseBody
    ResponseTodoDto modifyTodoById(@PathVariable("id") Long id, @RequestBody RequestTodoDto dto) {
        log.info("modifyTodoById={}", dto);
        dto.setId(id);
        this.checkRequired(dto);
        return ResponseTodoDto.builder().result(todoservice.updateTodo(dto)).build();
    }


    @PostMapping("/todolist")
    public @ResponseBody
    ResponseTodoDto addTodo(@RequestBody RequestTodoDto dto) {
        log.info("addTodo={}", dto);
        return ResponseTodoDto.builder().result(todoservice.addTodo(dto)).build();
    }

    public void checkRequired(RequestTodoDto dto){
        if(StringUtils.isBlank(dto.getContents())){
            throw new ValidCustomException("input error = {\"contents\":\"anythings...\"}");
        }
    }
}
