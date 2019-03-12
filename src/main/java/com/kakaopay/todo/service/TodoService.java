package com.kakaopay.todo.service;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.mybatis.mapper.TodoMapper;
import com.kakaopay.todo.mybatis.model.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService {
    private final TodoMapper todomapper;

    @Autowired
    public TodoService(TodoMapper todomapper){
        this.todomapper = todomapper;
    }

    public Todo getTodoById(RequestTodoDto dto){
        return Optional.ofNullable(todomapper.selectTodoById(dto)).get();
    }

    public List<Todo> getAllTodo(RequestTodoDto dto){
        return todomapper.selectAllTodo(dto);
    }

    public Long addTodo(RequestTodoDto dto){
        todomapper.insertTodo(dto);
        return dto.getId();
    }

    public int addRefTodo(RequestTodoDto dto){
        return todomapper.insertRefTodo(dto);
    }
}
