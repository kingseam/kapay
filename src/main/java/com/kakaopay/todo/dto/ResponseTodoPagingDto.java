package com.kakaopay.todo.dto;

import com.kakaopay.todo.mybatis.model.Todo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ResponseTodoPagingDto<T> extends ResponseTodoDto<T>{
    private final String totalCount;
    private final String limit;
    private final String offset;

    @Builder(builderMethodName = "childBuilder")
    private ResponseTodoPagingDto(T dto,String totalCount, String limit, String offset){
        super("", dto);
        this.totalCount = totalCount;
        this.limit = limit;
        this.offset = offset;
    }
}