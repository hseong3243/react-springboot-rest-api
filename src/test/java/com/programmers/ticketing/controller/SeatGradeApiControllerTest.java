package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.dto.seatgrade.SeatGradeDto;
import com.programmers.ticketing.dto.seatgrade.SeatGradeCreateRequest;
import com.programmers.ticketing.service.SeatGradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeatGradeApiController.class)
class SeatGradeApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SeatGradeService seatGradeService;

    @Test
    @DisplayName("성공: seatGrade 목록 조회 요청")
    void findSeatGrades() throws Exception {
        //given
        SeatGrade vip = new SeatGrade("vip");
        SeatGrade royal = new SeatGrade("royal");
        List<SeatGrade> seatGrades = List.of(vip, royal);
        List<SeatGradeDto> seatGradeDtos = seatGrades.stream()
                .map(SeatGradeDto::from)
                .toList();

        given(seatGradeService.findSeatGrades()).willReturn(seatGradeDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/seat-grades")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").isString());
    }

    @Test
    @DisplayName("성공: seatGrade 단건 생성 요청")
    void registerSeatGrade() throws Exception {
        //given
        SeatGradeCreateRequest request = new SeatGradeCreateRequest("vip");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/seat-grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    @DisplayName("성공: seatGrade 단건 삭제 요청")
    void deleteSeatGrade() throws Exception {
        //given
        Long seatGradeId = 1L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/seat-grades/" + seatGradeId));

        //then
        resultActions.andExpect(status().isNoContent());
    }
}