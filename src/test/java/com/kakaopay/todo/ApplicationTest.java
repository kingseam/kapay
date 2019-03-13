package com.kakaopay.todo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void 조회테스트() throws Exception{
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

        // 상태조회
        this.mockMvc.perform(get("/todos?statusType=Y")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.size", is(1)));

        // 상태조회
        this.mockMvc.perform(get("/todos?statusType=N")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.size", is(1)));

        // 내용조회
        this.mockMvc.perform(get("/todos?contents=sample")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.size", is(1)));

        // 내용조회
        this.mockMvc.perform(get("/todos?contents=ascascasc")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.size", is(0)));
    }

    @Test
    public void 할일추가후내용매칭() throws Exception{
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
    public void 할일추가후내용변경() throws Exception{
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

}
