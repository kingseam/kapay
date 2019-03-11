package com.kakaopay.todo.mybatis.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Alias("todo")
public class Todo {
    private Long id;
    private String contents;
    private String statusType;
    private LocalDateTime regDts;
    private LocalDateTime modDts;

    @Builder
    private Todo(Long id, String contents, String statusType, LocalDateTime modDts, LocalDateTime regDts){
        this.id = id;
        this.contents = contents;
        this.statusType = statusType;
        this.regDts = regDts;
        this.modDts = modDts;
    }

}
