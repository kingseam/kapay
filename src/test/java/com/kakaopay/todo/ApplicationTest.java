package com.kakaopay.todo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakaopay.todo.util.TodoStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void 메인테스트() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void 할일_목록_조회() throws Exception{
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"sample\" , \"statusType\":\"Y\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        // 전체조회
        this.mockMvc.perform(get("/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(2)));

        // 상태조회 (statusType = N)
        this.mockMvc.perform(get("/todos?statusType=Y")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(1)));

        // 상태조회 (statusType = Y)
        this.mockMvc.perform(get("/todos?statusType=N")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(1)))
            .andExpect(jsonPath("$.result[0].statusType", is("N")));

        // 내용조회 (contents = sample)
        this.mockMvc.perform(get("/todos?contents=sample")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(1)))
            .andExpect(jsonPath("$.result[0].contents", is("sample")));

        // 내용조회 (contents = ascascasc)
        this.mockMvc.perform(get("/todos?contents=ascascasc")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(0)));

        // offset, limit 조회 (paging)
        this.mockMvc.perform(get("/todos?offset=0&limit=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(1)));

        // 날짜 조회
        this.mockMvc.perform(get("/todos?modDtsBefore=2018-03-10T12:00:00&modDtsAfter=2018-03-10T12:00:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(0)));

        // 날짜 조회
        this.mockMvc.perform(get("/todos?modDtsBefore=2020-03-13T12:00:00&modDtsAfter=2018-03-10T12:00:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(2)));

        // id list 조회
        this.mockMvc.perform(get("/todos?ids=1,3")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(1)));

        // id list 조회
        this.mockMvc.perform(get("/todos?ids=1,2")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", hasSize(2)));
    }

    @Test
    public void 할일_추가_매핑없음() throws Exception{
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result[0].contents", is("test todo")));

    }

    @Test
    public void 할일_수정_매핑없음() throws Exception{
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result[0].contents", is("test todo")));

        this.mockMvc.perform(put("/todos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"1\", \"contents\":\"sample\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result[0].contents", is("sample")));
    }

    @Test
    public void 참조_리스트_테스트(){

        TodoStringUtils.getTodoMappingList("3","123123123 @1 @2");
        System.out.println(TodoStringUtils.getIdsList(TodoStringUtils.getTodoMappingList("3","123123123 @1 @2")));
    }

}
