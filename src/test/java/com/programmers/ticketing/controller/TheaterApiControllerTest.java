package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.TheaterDto;
import com.programmers.ticketing.service.TheaterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.createTheater;
import static com.programmers.ticketing.TicketingTestUtil.createTheaters;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TheaterApiController.class)
class TheaterApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TheaterService theaterService;

    @Test
    @DisplayName("성공: theater 목록 조회 요청")
    void findTheaters() throws Exception {
        //given
        List<Theater> theaters = createTheaters(5);
        List<TheaterDto> theaterDtos = theaters.stream()
                .map(TheaterDto::from)
                .toList();

        given(theaterService.findTheaters()).willReturn(theaterDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/theaters")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").isString())
                .andExpect(jsonPath("$.data[0].address").isString());
    }

    @Test
    @DisplayName("성공: theater 단건 생성 요청")
    void registerTheater() throws Exception {
        //given
        TheaterCreateRequest request = new TheaterCreateRequest("theater", "address");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        given(theaterService.registerTheater(any(), any())).willReturn(1L);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/theaters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    @DisplayName("성공: theater 단건 조회 요청")
    void findTheater() throws Exception {
        //given
        Long theaterId = 1L;
        Theater theater = createTheater("theater");
        TheaterDto theaterDto = TheaterDto.from(theater);

        given(theaterService.findTheater(any())).willReturn(theaterDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/theaters/" + theaterId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.name").isString())
                .andExpect(jsonPath("$.data.address").isString());
    }

    @Test
    @DisplayName("성공: theater 단건 업데이트 요청")
    void updateTheater() throws Exception {
        //given
        Long theaterId = 1L;
        TheaterUpdateRequest request = new TheaterUpdateRequest("updateName", "updateAddress");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(patch("/api/v1/theaters/" + theaterId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("성공: theater 단건 삭제 요청")
    void deleteTheater() throws Exception {
        //given
        Long theaterId = 1L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/theaters/" + theaterId));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}