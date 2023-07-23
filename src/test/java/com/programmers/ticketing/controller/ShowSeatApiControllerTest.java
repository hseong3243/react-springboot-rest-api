package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.showseat.ShowSeatBulkCreateRequest;
import com.programmers.ticketing.dto.showseat.ShowSeatCreateRequest;
import com.programmers.ticketing.dto.showseat.ShowSeatDto;
import com.programmers.ticketing.service.ShowSeatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.createShowSeat;
import static com.programmers.ticketing.TicketingTestUtil.createShowSeats;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowSeatApiController.class)
class ShowSeatApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShowSeatService showSeatService;

    @Test
    @DisplayName("성공: showSeat 목록 조회 요청")
    void findShowSeats() throws Exception {
        //given
        List<ShowSeatDto> showSeatDtos = createShowSeats(10)
                .stream()
                .map(ShowSeatDto::from)
                .toList();

        given(showSeatService.findShowSeats(any())).willReturn(showSeatDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/show-seats")
                .param("showInformationId", "1")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.*.seat").isNotEmpty());
    }

    @Test
    @DisplayName("성공: showSeat 단건 조회 요청")
    void findShowSeat() throws Exception {
        //given
        Long showSeatId = 1L;
        ShowSeat showSeat = createShowSeat();
        ShowSeatDto showSeatDto = ShowSeatDto.from(showSeat);

        given(showSeatService.findShowSeat(any())).willReturn(showSeatDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/show-seats/" + showSeatId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.seat").isNotEmpty())
                .andExpect(jsonPath("$.data.fee").isNumber());
    }

    @Test
    @DisplayName("성공: showSeat 다건 생성 요청")
    void registerShowSeats() throws Exception {
        //given
        ShowSeatBulkCreateRequest request = new ShowSeatBulkCreateRequest(1L, 1L, List.of(1L, 2L), 100);
        String jsonRequestPayload = mapper.writeValueAsString(request);

        given(showSeatService.registerShowSeats(any(), any(), any(), anyInt())).willReturn(List.of(1L, 2L));

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/show-seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray());
    }
}