package com.kakaopay.todo.mybatis.model;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@Alias("reftodo")
public class RefTodo {
    private Long id;
    private Long refId;

}
