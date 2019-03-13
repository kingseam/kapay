package com.kakaopay.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
@Getter
public class ResponseTodoDto<T> {
    private final String code;
    private final T result;

    @Builder
    protected ResponseTodoDto(String code, T result){
        this.code = StringUtils.defaultString(code,"200");
        this.result = result;
    }
}