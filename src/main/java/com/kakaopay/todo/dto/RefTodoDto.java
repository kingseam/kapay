package com.kakaopay.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RefTodoDto {

    private final Long id;
    private final Long refId;

    @Builder
    private RefTodoDto(String id, String refId) {
        this.id = Long.valueOf(id);
        this.refId = Long.valueOf(refId);
    }
}
