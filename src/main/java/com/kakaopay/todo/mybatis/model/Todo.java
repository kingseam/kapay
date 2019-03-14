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
@Alias("todo")
public class Todo {
    private Long id;
    private String contents;
    private String statusType;
    private LocalDateTime regDts;
    private LocalDateTime modDts;
}
