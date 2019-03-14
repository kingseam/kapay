package com.kakaopay.todo.service;

import com.kakaopay.todo.dto.RefTodoDto;
import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.mybatis.mapper.TodoMapper;
import com.kakaopay.todo.mybatis.model.Todo;
import com.kakaopay.todo.mybatis.model.TodoAccum;
import com.kakaopay.todo.util.TodoStringUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TodoService {

    private final TodoMapper todomapper;

    public TodoService(TodoMapper todomapper) {
        this.todomapper = todomapper;
    }

    public Todo getTodoById(RequestTodoDto dto) {
        return todomapper.selectTodoById(dto);
    }

    public TodoAccum getTodoAccum() {
        return todomapper.selectTodoAccum();
    }

    @Transactional
    public int updateTodo(RequestTodoDto dto) {
        this.availability(dto);
        if (StringUtils.equals(dto.getStatusType(), "Y")) {
            if (this.selectStatusType(dto.getId()) > 0) {
                throw new ValidCustomException("완료되지 않은 참조 할일이 있습니다.");
            }
        }
        return todomapper.updateTodo(dto);
    }

    public List<Todo> getAllTodo(RequestTodoDto dto) {
        return todomapper.selectAllTodo(dto);
    }

    @Transactional
    public int addTodo(RequestTodoDto dto) {
        // 조회수 증가. 집계성은 배치로 하는편이지만 todo에선 실시간으로 처리.
        todomapper.updateTodoAccum();
        int result = todomapper.insertTodo(dto);
        this.availability(dto);
        if (StringUtils.equals(dto.getStatusType(), "N")) {
            if (this.selectRefPossible(dto.getId()) > 0) {
                throw new ValidCustomException("참조 할일이 완료인 경우 추가 할수 없습니다.");
            }
        }
        return result;
    }

    public void availability(RequestTodoDto dto) {
        List<RefTodoDto> refTodoList = TodoStringUtils.getTodoMappingList(String.valueOf(dto.getId()), dto.getContents());
        this.deleteRefTodo(dto.getId());
        for (RefTodoDto refTodoDto : refTodoList) {
            if (this.selectBackreference(refTodoDto) > 0) {
                throw new ValidCustomException("역참조는 불가능 합니다.");
            }

            if (this.selectExistTodo(Long.valueOf(refTodoDto.getRefId())) > 0) {
                this.addRefTodo(refTodoDto);
            } else {
                throw new ValidCustomException("없는 id는 참조할수 없습니다.");
            }
        }

    }

    private void addRefTodo(RefTodoDto dto) {
        todomapper.insertRefTodo(dto);
    }

    private int selectExistTodo(Long id) {
        return todomapper.selectExistTodo(id);
    }

    private int selectRefPossible(Long id) {
        return todomapper.selectRefPossible(id);
    }

    private int selectStatusType(Long id) {
        return todomapper.selectStatusType(id);
    }

    private int selectBackreference(RefTodoDto dto) {
        return todomapper.selectBackreference(dto);
    }

    private int deleteRefTodo(Long id) {
        return todomapper.deleteRefTodo(id);
    }
}
