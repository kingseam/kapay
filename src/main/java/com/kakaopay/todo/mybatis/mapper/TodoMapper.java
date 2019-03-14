package com.kakaopay.todo.mybatis.mapper;

import com.kakaopay.todo.dto.RefTodoDto;
import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.mybatis.model.Todo;
import com.kakaopay.todo.mybatis.model.TodoAccum;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper {

    Todo selectTodoById(RequestTodoDto todo);

    int updateTodo(RequestTodoDto todo);

    int selectExistTodo(Long id);

    List<Todo> selectAllTodo(RequestTodoDto todo);

    int insertTodo(RequestTodoDto todo);

    int insertRefTodo(RefTodoDto refTodo);

    void updateTodoAccum();

    TodoAccum selectTodoAccum();

    int selectRefPossible(Long id);

    int selectStatusType(Long id);

    int selectBackreference(RefTodoDto refTodo);

    int deleteRefTodo(Long id);
}
