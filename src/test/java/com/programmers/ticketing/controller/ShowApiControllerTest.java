package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.show.ShowCreateRequest;
import com.programmers.ticketing.dto.show.ShowDto;
import com.programmers.ticketing.dto.show.ShowUpdateRequest;
import com.programmers.ticketing.service.ShowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowApiController.class)
class ShowApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShowService showService;

    @Test
    @DisplayName("성공: show 목록 조회 요청")
    void findShows() throws Exception {
        //given
        List<Show> shows = createShows(5);
        List<ShowDto> showDtos = shows.stream()
                .map(ShowDto::from)
                .toList();

        given(showService.findShows()).willReturn(showDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/shows")
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title").isString())
                .andExpect(jsonPath("$.data[0].showType").isString())
                .andExpect(jsonPath("$.data[0].playtime").isString());
    }

    @Test
    @DisplayName("성공: show 단건 생성 요청")
    void registerShow() throws Exception {
        //given
        ShowCreateRequest request = new ShowCreateRequest("title", ShowType.CONCERT, LocalTime.of(2, 30), "");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/shows")
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
    @DisplayName("성공: show 단건 조회 요청")
    void findShow() throws Exception {
        //given
        Long showId = 1L;
        Show show = createShow("show");
        ShowDto showDto = ShowDto.from(show);

        given(showService.findShow(any())).willReturn(showDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/shows/" + showId)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.title").isString())
                .andExpect(jsonPath("$.data.showType").isString())
                .andExpect(jsonPath("$.data.playtime").isString());
    }

    @Test
    @DisplayName("성공: show 단건 업데이트 요청")
    void updateShow() throws Exception {
        //given
        Long showId = 1L;
        ShowUpdateRequest request = new ShowUpdateRequest(LocalTime.of(2, 30), "description");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(patch("/api/v1/shows/" + showId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("성공: show 단건 삭제 요청")
    void deleteShow() throws Exception {
        //given
        Long showId = 1L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/shows/" + showId));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}