package com.kakaopay.todo.service;

import com.kakaopay.todo.dto.RefTodoDto;
import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.mybatis.mapper.TodoMapper;
import com.kakaopay.todo.mybatis.model.Todo;
import com.kakaopay.todo.mybatis.model.TodoAccum;
import com.kakaopay.todo.util.TodoStringUtils;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TodoService {
    private final TodoMapper todomapper;

    public TodoService(TodoMapper todomapper){
        this.todomapper = todomapper;
    }

    public Todo getTodoById(RequestTodoDto dto){
        return todomapper.selectTodoById(dto);
    }

    public TodoAccum getTodoAccum(){
        return todomapper.selectTodoAccum();
    }

    @Transactional
    public int updateTodo(RequestTodoDto dto){
        return todomapper.updateTodo(dto);
    }

    public List<Todo> getAllTodo(RequestTodoDto dto){
        return todomapper.selectAllTodo(dto);
    }

    @Transactional
    public int addTodo(RequestTodoDto dto){
        // 조회수 증가. 집계성은 배치로 하는편이지만 todo에선 실시간으로 처리.
        todomapper.updateTodoAccum();
        todomapper.insertTodo(dto);
        log.info("dto.getId()====={}", dto.getId());
        List<RefTodoDto> refTodoList = TodoStringUtils.getTodoMappingList(String.valueOf(dto.getId()), dto.getContents());
        for(RefTodoDto refTodoDto : refTodoList){
            log.info("====REF_ID==={}",refTodoDto.getRefId());
            if(this.selectExistTodo(Long.valueOf(refTodoDto.getRefId())) > 0 ) {
                this.addRefTodo(refTodoDto);
            }else{
                throw new ValidCustomException("없는 id는 참조할수 없습니다.");
            }
        }

        return 0;
    }

    public void addRefTodo(RefTodoDto dto){
        todomapper.insertRefTodo(dto);
    }

    public int selectExistTodo(Long id){
        return todomapper.selectExistTodo(id);
    }
}
