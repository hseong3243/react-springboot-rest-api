package com.programmers.ticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import com.programmers.ticketing.dto.showinformation.ShowInformationCreateRequest;
import com.programmers.ticketing.dto.showinformation.ShowInformationUpdateRequest;
import com.programmers.ticketing.dto.showinformation.ShowInformationDto;
import com.programmers.ticketing.service.ShowInformationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.programmers.ticketing.TicketingTestUtil.createShowInformation;
import static com.programmers.ticketing.TicketingTestUtil.createShowInformations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShowInformationApiController.class)
class ShowInformationApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShowInformationService showInformationService;

    @Test
    @DisplayName("성공: showInformation 목록 조회")
    void findShowInformations() throws Exception {
        //given
        List<ShowInformation> showInformations = createShowInformations(5);
        List<ShowInformationDto> showInformationDtos = showInformations.stream()
                .map(ShowInformationDto::from)
                .toList();

        given(showInformationService.findShowInformations(any(), any(), any())).willReturn(showInformationDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/show-informations")
                .accept(MediaType.APPLICATION_JSON)
                .param("showId", "1")
                .param("theaterId", "1")
                .param("startTime", "2023-07-19 14:56:23"));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].show").isNotEmpty())
                .andExpect(jsonPath("$.data[0].theater").isNotEmpty());
    }

    @Test
    @DisplayName("성공: showInformation 단건 생성")
    void registerShowInformation() throws Exception {
        //given
        ShowInformationCreateRequest request = new ShowInformationCreateRequest(
                1L,
                1L,
                LocalDateTime.now().plusHours(1));
        String jsonRequestPayload = mapper.writeValueAsString(request);

        given(showInformationService.registerShowInformation(any(), any(), any())).willReturn(1L);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/show-informations")
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
    @DisplayName("성공: showInformation 단건 조회")
    void findShowInformation() throws Exception {
        //given
        ShowInformation showInformation = createShowInformation();
        ShowInformationDto showInformationDto = ShowInformationDto.from(showInformation);

        given(showInformationService.findShowInformation(any())).willReturn(showInformationDto);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/show-informations/" + 1L)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.show").isNotEmpty())
                .andExpect(jsonPath("$.data.theater").isNotEmpty());
    }

    @Test
    @DisplayName("성공: showInformation 단건 업데이트")
    void updateShowInformation() throws Exception {
        //given
        ShowInformationUpdateRequest request = new ShowInformationUpdateRequest(1L, ShowStatus.STAGING, LocalDateTime.now());
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(patch("/api/v1/show-informations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestPayload));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("성공: showInformation 단건 삭제")
    void deleteShowInformation() throws Exception {
        //given
        Long showInformationId = 1L;

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/show-informations/" + showInformationId));

        //then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}