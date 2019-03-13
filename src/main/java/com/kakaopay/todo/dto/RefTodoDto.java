package com.kakaopay.todo.dto;

import lombok.Builder;

public class RefTodoDto {
    private final String id;
    private final String refId;

    @Builder
    private RefTodoDto(String id, String refId){
        this.id = id;
        this.refId = refId;
    }
}
