package com.kakaopay.todo.mybatis.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@Alias("reftodo")
public class RefTodo {
    private Long id;
    private Long ref_id;

}
