package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.dto.seat.SeatCreateRequest;
import com.programmers.ticketing.dto.seat.SeatDto;
import com.programmers.ticketing.service.SeatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.createSeat;
import static com.programmers.ticketing.TicketingTestUtil.createSeats;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatApiController.class)
class SeatApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SeatService seatService;

    @Test
    @DisplayName("성공: seat 목록 요청 - theaterId")
    void findSeats() throws Exception {
        //given
        List<Seat> seats = createSeats(5);
        List<SeatDto> seatDtos = seats.stream()
                .map(SeatDto::from)
                .toList();

        given(seatService.findSeats(any())).willReturn(seatDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/seats")
                .accept(MediaType.APPLICATION_JSON)
                .param("theaterId", "1"));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].theater").isNotEmpty())
                .andExpect(jsonPath("$.data[0].position").isNotEmpty());
    }

    @Test
    @DisplayName("성공: seat 단건 생성 요청")
    void registerSeat() throws Exception {
        //given
        SeatCreateRequest request = new SeatCreateRequest(1L, 1, 1, 1);
        String jsonRequestPayload = mapper.writeValueAsString(request);

        given(seatService.registerSeats(any(), anyInt(), anyInt(), anyInt())).willReturn(List.of(1L));

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("성공: seat 단건 조회 요청")
    void findSeat() throws Exception {
        //given
        Long seatId = 1L;
        Seat seat = createSeat();
        SeatDto seatDto = SeatDto.from(seat);

        given(seatService.findSeat(any())).willReturn(seatDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/seats/" + seatId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.theater").isNotEmpty())
                .andExpect(jsonPath("$.data.position").isNotEmpty());
    }

    @Test
    @DisplayName("성공: seat 단건 삭제 요청")
    void deleteSeat() throws Exception {
        //given
        Long seatId = 1L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/seats/" + seatId));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}