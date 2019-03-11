package com.kakaopay.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "contents", "statusType"})
public class RequestTodoDto {
    private Long id;
    private String contents;
    private String statusType;
    private String modDts;
    private String regDts;

    @JsonIgnore
    private List<RefTodoDto> refTodoList;

    @Data
    class RefTodoDto {
        private Long id;
        private Long ref_id;
    }
}