package com.kakaopay.todo.mybatis.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Alias("reftodo")
public class RefTodo {
    private Long id;
    private Long refId;

}
