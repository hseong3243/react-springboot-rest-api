package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.dto.reservation.ReservationCreateRequest;
import com.programmers.ticketing.dto.reservation.ReservationDto;
import com.programmers.ticketing.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.createReservations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationApiController.class)
class ReservationApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("성공: reservation 목록 요청")
    void findReservations() throws Exception {
        //given
        List<ReservationDto> reservationDtos = createReservations(10)
                .stream()
                .map(ReservationDto::from)
                .toList();

        given(reservationService.findReservations(any())).willReturn(reservationDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.*.theater").isNotEmpty())
                .andExpect(jsonPath("$.data.*.seat").isNotEmpty())
                .andExpect(jsonPath("$.data.*.show").isNotEmpty());
    }

    @Test
    @DisplayName("성공: reservation 단건 생성 요청")
    void createReservation() throws Exception {
        //given
        ReservationCreateRequest request = new ReservationCreateRequest(List.of(1L, 2L), "email@gmail.com");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        given(reservationService.createReservation(any(), any())).willReturn(List.of(1L));

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray());

    }

    @Test
    @DisplayName("성공: reservation 단건 조회 요청")
    void findReservation() throws Exception {
        //given
        Reservation reservation = TicketingTestUtil.createReservation();
        ReservationDto reservationDto = ReservationDto.from(reservation);

        given(reservationService.findReservation(any())).willReturn(reservationDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/reservations/" + 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.theater").isNotEmpty())
                .andExpect(jsonPath("$.data.seat").isNotEmpty())
                .andExpect(jsonPath("$.data.show").isNotEmpty());

    }

    @Test
    @DisplayName("성공: reservation 단건 조회 삭제")
    void deleteReservation() throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/reservations/" + 1L))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNoContent());
    }
}