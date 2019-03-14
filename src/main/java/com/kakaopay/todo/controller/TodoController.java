package com.kakaopay.todo.controller;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.dto.ResponseTodoDto;
import com.kakaopay.todo.dto.ResponseTodoPagingDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.mybatis.model.TodoAccum;
import com.kakaopay.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TodoController {

    private final TodoService todoservice;

    public TodoController(TodoService todoservice) {
        this.todoservice = todoservice;
    }

    @GetMapping("/todos")
    public ResponseTodoPagingDto getAll(RequestTodoDto dto) {
        TodoAccum todoAccum = todoservice.getTodoAccum();
        return ResponseTodoPagingDto.childBuilder().dto(todoservice.getAllTodo(dto)).limit(dto.getLimit()).offset(dto.getOffset()).totalCount(todoAccum.getTotalCount()).build();
    }

    @GetMapping("/todos/{id}")
    public ResponseTodoDto getTodoById(RequestTodoDto dto) {
        return ResponseTodoDto.builder().result(todoservice.getTodoById(dto)).build();
    }

    @PutMapping({"/todos/{id}", "/todos"})
    public ResponseTodoDto modifyTodoById(@RequestBody RequestTodoDto dto) {
        // vaildation check
        if (dto.getId() == null) {
            throw new ValidCustomException("Required value (id) = {\"id\":\"1\", \"contents\":\"sample\"}");
        }

        return ResponseTodoDto.builder().result(todoservice.updateTodo(dto)).build();
    }

    @PostMapping("/todos")
    public ResponseTodoDto addTodo(@RequestBody RequestTodoDto dto) {
        if (!StringUtils.equals("Y", dto.getStatusType())) {
            dto.setStatusType("N");
        }
        return ResponseTodoDto.builder().result(todoservice.addTodo(dto)).build();
    }
}
