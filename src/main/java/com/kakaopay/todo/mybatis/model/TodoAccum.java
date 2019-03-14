package com.kakaopay.todo.mybatis.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Alias("todo_accum")
public class TodoAccum {

    private String totalCount;
    private LocalDateTime modDts;
    private LocalDateTime regDts;
}
