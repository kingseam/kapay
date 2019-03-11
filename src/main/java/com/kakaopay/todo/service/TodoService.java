package com.kakaopay.todo.service;

import com.kakaopay.todo.mybatis.mapper.TodoMapper;
import com.kakaopay.todo.mybatis.model.RefTodo;
import com.kakaopay.todo.mybatis.model.Todo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService {

    @Autowired
    TodoMapper todomapper;

    public Todo getTodoById(Todo todo){
        return todomapper.selectTodoById(todo);
    }

    public List<Todo> getAllTodo(){
        return todomapper.selectAllTodo();
    }

    public int addTodo(Todo todo){
        return todomapper.insertTodo(todo);
    }

    public int addRefTodo(RefTodo refTodo){
        return todomapper.insertRefTodo(refTodo);
    }
}
