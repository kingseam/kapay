package com.kakaopay.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String modDtsAfter;
    private String modDtsBefore;
    private String regDtsAfter;
    private String regDtsBefore;

    //다중건에 대한 샘플링.. 개인적으론 id[]=[1,2,3] 형태가 더 좋아보이지만 일단은 개발..
    private List<Long> ids;
    @JsonIgnore
    private List<RefTodoDto> refTodoList;

    @Data
    class RefTodoDto {
        private Long id;
        private Long refId;
    }
}