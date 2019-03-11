package com.kakaopay.todo.mybatis.mapper;

import com.kakaopay.todo.mybatis.model.RefTodo;
import com.kakaopay.todo.mybatis.model.Todo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
    Todo selectTodoById(Todo todo);
    List<Todo> selectAllTodo();
    int insertTodo(Todo todo);
    int insertRefTodo(RefTodo refTodo);

}
