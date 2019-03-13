package com.kakaopay.todo.mybatis.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Getter
@Alias("reftodo")
public class RefTodo {
    private Long id;
    private Long refId;

    @Builder
    private RefTodo(Long id, Long refId){
        this.id = id;
        this.refId = refId;
    }
}
