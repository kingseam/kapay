package com.kakaopay.todo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
@Data
@NoArgsConstructor
public class RequestTodoDto {
    private Long id;
    private String contents;
    private String statusType;
    private String modDtsAfter;
    private String modDtsBefore;
    private String regDtsAfter;
    private String regDtsBefore;
    private String offset;
    private String limit;

    //다중건에 대한 샘플링.. 개인적으론 id[]=[1,2,3] 형태가 더 좋아보이지만 일단은 개발..
    private List<Long> ids;

    public String getOffset(){
        return StringUtils.defaultString(this.offset, "0");
    }

    public String getLimit(){
        return StringUtils.defaultString(this.limit, "10");
    }

    public String getStatusType(){
        return !StringUtils.equals("Y", this.statusType) ? "N" : "Y";
    }
}