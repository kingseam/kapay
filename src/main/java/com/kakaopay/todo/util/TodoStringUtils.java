package com.kakaopay.todo.util;

import com.kakaopay.todo.dto.RefTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TodoStringUtils {
    static public List<RefTodoDto> getTodoMappingList(String id, String contents){
        /*
         * 과제 기준으로 1번 요소를 할일로 보고 다음부터는 참조 요소로 본다.
         * 다만, 예외 케이스가 들어오면 로직 수정. api format 에 따로 추가하는게 어떨가 싶다..
         */
        CopyOnWriteArrayList<RefTodoDto> resultList = new CopyOnWriteArrayList<>();
        StringTokenizer token = new StringTokenizer(contents,"@");
        if(token.countTokens() > 1) {
            // 첫 요소 skip == stream.skip(1)
            token.nextToken();
            while (token.hasMoreTokens()) {
                String temp = token.nextToken();
                log.info("===temp=={}, id===={}",temp.trim(), id);
                if (temp.trim().equals(id)) {
                    throw new ValidCustomException("자신의 id는 참조할수 없습니다.");
                }
                resultList.add(RefTodoDto.builder().id(id).refId(temp.trim()).build());
            }
        }
        return resultList;
    }

    static public List<String> getIdsList(List<RefTodoDto> refTodoList){
        return refTodoList.stream().map(obj->obj.getRefId()).collect(Collectors.toList());
    }
}
