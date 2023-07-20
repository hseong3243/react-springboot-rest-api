package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalControllerAdviceTest {
    private MockMvc mvc;

    @RestController
    static class ExceptionController {
        @GetMapping("/noSuchElementEx")
        public void throwNoSuchElementEx() {
            throw new NoSuchElementException("No such element ex");
        }

        @GetMapping("/duplicateKeyEx")
        public void throwDuplicateKeyEx() {
            throw new DuplicateKeyException("Duplicate email exist");
        }

        @GetMapping("/illegalArgumentEx")
        public void throwIllegalArgumentEx() {
            throw new IllegalArgumentException("Illegal argument input");
        }

        @PostMapping("/methodArgumentNotValidEx")
        public void throwMethodArgumentNotValidEx(@RequestBody @Valid TestRequest request) {

        }
    }

    static class TestRequest {
        @NotNull
        private Long testId;

        public TestRequest() {
        }

        public TestRequest(Long testId) {
            this.testId = testId;
        }

        public Long getTestId() {
            return testId;
        }
    }

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(new ExceptionController())
                .setControllerAdvice(new GlobalControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("성공: NoSuchElementException 처리")
    void noSuchElementExHandle() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(get("/noSuchElementEx")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("성공: DuplicateKeyException 처리")
    void duplicateKeyExHandle() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(get("/duplicateKeyEx")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(409))
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("성공: IllegalArgumentExHandle 처리")
    void illegalArgumentExHandle() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(get("/illegalArgumentEx")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("성공: MethodArgumentNotValidException 처리")
    @Disabled("수정 필요")
    void methodArgumentNotValidEx() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(post("/methodArgumentNotValidEx")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"testId\":}")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isString());
    }

}