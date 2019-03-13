package com.kakaopay.todo.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class RefTodoDto {
    private final String id;
    private final String refId;

    @Builder
    private RefTodoDto(String id, String refId, List<String> refIds){
        this.id = id;
        this.refId = refId;
    }
}
