package com.kakaopay.todo.mybatis.mapper;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.mybatis.model.Todo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {
    Todo selectTodoById(RequestTodoDto todo);
    int updateTodo(RequestTodoDto todo);
    List<Todo> selectAllTodo(RequestTodoDto todo);
    int insertTodo(RequestTodoDto todo);
    int insertRefTodo(RequestTodoDto todo);

}
