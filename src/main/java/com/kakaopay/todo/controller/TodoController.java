package com.kakaopay.todo.controller;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.mybatis.model.Todo;
import com.kakaopay.todo.service.TodoService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TodoController {

    @Autowired
    TodoService todoservice;

    @GetMapping("/")
    public @ResponseBody String main(){
        log.info("{}",this);
        return "main";
    }

    @GetMapping("/todolist")
    public @ResponseBody List<Todo> getAll(){
        log.info("getAll()");
        return todoservice.getAllTodo();

    }

    @GetMapping("/todolist/{id}")
    public @ResponseBody Todo getTodoById(@PathVariable("id") Long id, RequestTodoDto dto){
        log.info("get={}",dto);
        Todo todo = Todo.builder()
            .id(id)
            .contents(dto.getContents())
            .statusType(dto.getStatusType())

            .build();
        return todoservice.getTodoById(todo);
    }


    @PostMapping("/todolist")
    public @ResponseBody int addTodo(@RequestBody RequestTodoDto dto){
        log.info("post={}", dto);
        Todo todo = Todo.builder()
                    .contents(dto.getContents())
                    .statusType("N")
                    .build();
        return todoservice.addTodo(todo);
    }
}
