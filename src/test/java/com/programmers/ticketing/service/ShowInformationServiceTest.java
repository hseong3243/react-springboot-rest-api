package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.*;
import com.programmers.ticketing.dto.ShowDto;
import com.programmers.ticketing.dto.TheaterDto;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.programmers.ticketing.TicketingTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShowInformationServiceTest {

    @InjectMocks
    private ShowInformationService informationService;

    @Mock
    private ShowInformationRepository showInformationRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @Test
    @DisplayName("성공: showInformation 단건 등록 기능")
    void registerShowInformation() {
        //given
        Show show = new Show("title", ShowType.CONCERT, LocalTime.of(2, 30), "");
        Theater theater = new Theater("theater", "address");

        given(showRepository.findById(any())).willReturn(Optional.of(show));
        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        LocalDateTime startTime = LocalDateTime.now().plusYears(100);
        informationService.registerShowInformation(1L, 1L, startTime);

        //then
        then(showInformationRepository).should().save(any());
    }

    @Test
    @DisplayName("예외: showInformation 단건 등록 기능 - 현재보다 이전의 starTime")
    void registerShowInformation_ButStartTimeBeforeNow_Then_Exception() {
        //given
        Show show = new Show("title", ShowType.CONCERT, LocalTime.of(2, 30), "");
        Theater theater = new Theater("theater", "address");

        given(showRepository.findById(any())).willReturn(Optional.of(show));
        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        LocalDateTime startTime = LocalDateTime.now().minusSeconds(1);

        //then
        assertThatThrownBy(() -> informationService.registerShowInformation(1L, 1L, startTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: showInformation 단건 조회 기능")
    void findShowInformation() {
        //given
        Show show = new Show("title", ShowType.CONCERT, LocalTime.of(2, 30), "");
        Theater theater = new Theater("theater", "address");
        LocalDateTime startTime = LocalDateTime.now().plusYears(100);
        ShowInformation showInformation = new ShowInformation(show, theater, startTime);

        given(showInformationRepository.findShowInformationWithShowAndTheater(any()))
                .willReturn(Optional.of(showInformation));

        //when
        ShowInformationDto findShowInformation = informationService.findShowInformation(1L);

        //then
        ShowDto showDto = ShowDto.from(show);
        TheaterDto theaterDto = TheaterDto.from(theater);
        assertThat(findShowInformation.getShowDto()).usingRecursiveComparison().isEqualTo(showDto);
        assertThat(findShowInformation.getTheaterDto()).usingRecursiveComparison().isEqualTo(theaterDto);
    }

    @Test
    @DisplayName("예외: showInformation 단건 조회 기능 - 존재하지 않는 showInformation")
    void findShowInformation_ButNoSuchElement_Then_Exception() {
        //given
        given(showInformationRepository.findShowInformationWithShowAndTheater(any()))
                .willReturn(Optional.empty());

        //when\
        //then
        assertThatThrownBy(() -> informationService.findShowInformation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("성공: showInformation 목록 조회 기능")
    void findShowInformations() {
        //given
        Show show = createShow("title");
        Theater theater = createTheater("theater");
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        ShowInformation showInformationA = new ShowInformation(show, theater, startTime);
        ShowInformation showInformationB = new ShowInformation(show, theater, startTime);
        List<ShowInformation> showInformations = List.of(showInformationA, showInformationB);

        given(showInformationRepository.findShowInformations(any(), any(), any()))
                .willReturn(showInformations);

        //when
        List<ShowInformationDto> result = informationService.findShowInformations(1L, 1L, startTime);

        //then
        ShowInformationDto showInformationDtoA = ShowInformationDto.from(showInformationA);
        ShowInformationDto showInformationDtoB = ShowInformationDto.from(showInformationB);
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(showInformationDtoA, showInformationDtoB);
    }

    @Test
    @DisplayName("성공: showInformation 단건 업데이트")
    void updateShowInformation() {
        //given
        Show show = createShow("show");
        Theater theater = createTheater("theater");
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        ShowInformation showInformation = new ShowInformation(show, theater, startTime);

        given(showInformationRepository.findById(any())).willReturn(Optional.of(showInformation));

        //when
        ShowStatus updateShowStatus = ShowStatus.STAGING;
        LocalDateTime updateStartTime = LocalDateTime.now().plusHours(2);
        informationService.updateShowInformation(1L, updateShowStatus, updateStartTime);

        //then
        assertThat(showInformation.getShowStatus()).isEqualTo(updateShowStatus);
        assertThat(showInformation.getStartTime()).isEqualToIgnoringNanos(updateStartTime);
    }

    @Test
    @DisplayName("성공: showInformation 단건 업데이트 - 존재하지 않는 showInformation")
    void updateShowInformation_ButNoSuchElement_Then_Exception() {
        //given
        given(showInformationRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> informationService.updateShowInformation(1L, null, null))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("예외: showInformation 단건 업데이트 - 현재보다 이전의 startTime")
    void updateShowInformation_ButStartTimeBeforeNow_Then_Exception() {
        //given
        Show show = createShow("show");
        Theater theater = createTheater("theater");
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        ShowInformation showInformation = new ShowInformation(show, theater, startTime);

        given(showInformationRepository.findById(any())).willReturn(Optional.of(showInformation));

        //when
        LocalDateTime updateStartTime = LocalDateTime.now().minusHours(1);

        //then
        assertThatThrownBy(() -> informationService.updateShowInformation(1L, null, updateStartTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: showInformation 단건 삭제 기능")
    void deleteShowInformation() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        ShowInformation showInformation = createShowInformation("show", "theater", startTime);

        given(showInformationRepository.findById(any())).willReturn(Optional.of(showInformation));

        //when
        informationService.deleteShowInformation(1L);

        //then
        then(showInformationRepository).should().delete(any());
    }

    @Test
    @DisplayName("예외: showInformation 단건 삭제 기능 - 존재하지 않는 showInformation")
    void deleteShowInformation_ButNoSuchElement_Then_Exception() {
        //given
        given(showInformationRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> informationService.deleteShowInformation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }
}