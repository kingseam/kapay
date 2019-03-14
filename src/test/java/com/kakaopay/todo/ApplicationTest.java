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

    // 전체테스트는 구현하지 못하고 단위 테스트만 구현

    @Test
    public void 할일_목록_조회() throws Exception {
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
    public void 할일_수정_매핑없음() throws Exception {
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
    public void 할일추가테스트() throws Exception {
        // id=1 (본인 참조)  == 400 코드 리턴
        // 참조할 할일(id)이 없으면 참조를 불가능하다.
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo @1\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("400")));

        // id=2
        // statusType을 지정하지 않으면 자동으로 N, YN으로 컬럼을 설정하려했으나 더 많은 행위가 추가될수 있을거 같아 타입으로 컬럼 생성
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // id=3 ( 참조 @2 ) 할일 추가 시 다른 할일들을 참조 걸 수 있다.
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test @2\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // 없는 참조 추가 == 400 코드 리턴
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test @100\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("400")));

        // 다건 참조 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고)
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test @3 @2\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // 완료 상태로 할일 추가
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"sample @3\" , \"statusType\":\"Y\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // 완료된 할일을 참조로 걸고 해당 할일의 완료여부를 'N'값으로 참조 추가 == 오류 400
        // 추가하는 할일의 완료상태가 N이면 참조되어있는 할일들의 상태가 전체 N이 되어야 한다. Y이면 불가능하다.
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"sample @6\" , \"statusType\":\"N\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("400")));

        // Long으로 cast되는 형태가 아니면 참조 되지 않는다.
        // 특정 예외로 잡는게 좋겠지만 auto casting이 되지 않는 경우 500으로 return하여 구현.
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"sample @가나다 @abc\" , \"statusType\":\"N\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("500")));
    }

    @Test
    public void 할일추가후_수정_조회테스트() throws Exception {
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test todo\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // ( 참조 @1 ) 할일 추가 시 다른 할일들을 참조 걸 수 있다.
        this.mockMvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"contents\":\"test @1\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // uri : /todos/ 수정 역참조
        this.mockMvc.perform(put("/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"1\", \"contents\":\"a @2\" , \"statusType\":\"Y\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("400")));

        // uri : /todos/{id}
        // 완료되지 않은 참조가 있을 경우
        this.mockMvc.perform(put("/todos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"1\", \"contents\":\"update\" , \"statusType\":\"Y\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("400")));

        // uri : /todos/{id}
        // 내용 수정
        this.mockMvc.perform(put("/todos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"1\", \"contents\":\"update\" , \"statusType\":\"N\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(1)));

        // 수정 후 contents 변경내역 확인
        this.mockMvc.perform(get("/todos/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.contents", is("update")));
    }
}
