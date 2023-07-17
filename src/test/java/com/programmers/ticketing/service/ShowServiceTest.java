package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.ShowDto;
import com.programmers.ticketing.repository.ShowRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    @InjectMocks
    private ShowService showService;

    @Mock
    private ShowRepository showRepository;

    @Test
    @DisplayName("성공: show 등록 기능")
    void registerShow() {
        //given
        String title = "title";
        ShowType showType = ShowType.valueOf("MUSICAL");
        LocalTime playtime = LocalTime.of(2, 30, 0);
        String description = "";

        //when
        showService.registerShow(title, showType, playtime, description);

        //then
        then(showRepository).should().save(any());
    }

    @Test
    @DisplayName("예외: show 등록 기능 - 범위를 넘은 description")
    void registerShow_ButDescriptionOutOfRange_Then_Exception() {
        //given
        String title = "title";
        ShowType showType = ShowType.valueOf("MUSICAL");
        LocalTime playtime = LocalTime.of(2, 30, 0);

        //when
        String description = "a".repeat(1001);

        //then
        assertThatThrownBy(() -> showService.registerShow(title, showType, playtime, description))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: show 단건 조회 기능")
    void findShow() {
        //given
        String title = "title";
        ShowType showType = ShowType.CONCERT;
        LocalTime playtime = LocalTime.of(2, 30);
        String description = "";
        Show show = new Show(title, showType, playtime, description);

        given(showRepository.findById(any())).willReturn(Optional.of(show));

        //when
        ShowDto showDto = showService.findShow(1L);

        //then
        assertThat(showDto.getTitle()).isEqualTo("title");
        assertThat(showDto.getShowType()).isEqualTo(ShowType.CONCERT);
        assertThat(showDto.getPlaytime()).isEqualTo(LocalTime.of(2, 30));
    }

    @Test
    @DisplayName("예외: show 단건 조회 기능 - 존재하지 않는 show")
    void findShow_ButNoSuchElement_Then_Exception() {
        //given
        given(showRepository.findById(any())).willThrow(NoSuchElementException.class);

        //when
        //then
        assertThatThrownBy(() -> showService.findShow(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("성공: show 목록 조회 기능")
    void findShows() {
        //given
        Show showA = new Show("titleA", ShowType.CONCERT, LocalTime.of(2, 30), "");
        Show showB = new Show("titleB", ShowType.CONCERT, LocalTime.of(2, 30), "");
        List<Show> shows = List.of(showA, showB);

        given(showRepository.findAll()).willReturn(shows);

        //when
        List<ShowDto> result = showService.findShows();

        //then
        ShowDto showDtoA = ShowDto.from(showA);
        ShowDto showDtoB = ShowDto.from(showB);
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(showDtoA, showDtoB);
    }

    @Test
    @DisplayName("성공: show 단건 업데이트")
    void updateShow() {
        //given
        Show show = new Show(
                "title",
                ShowType.CONCERT,
                LocalTime.of(2, 30),
                ""
        );

        given(showRepository.findById(any())).willReturn(Optional.of(show));

        //when
        LocalTime updatePlaytime = LocalTime.of(2, 0);
        String updateDescription = "new description";
        showService.updateShow(1L, updatePlaytime, updateDescription);

        //then
        assertThat(show.getPlaytime()).isEqualTo(LocalTime.of(2, 0));
        assertThat(show.getDescription()).isEqualTo("new description");
    }

    @Test
    @DisplayName("예외: show 단건 업데이트 - 범위를 넘은 description")
    void updateShow_ButDescriptionOutOfRange_Then_Exception() {
        //given
        Show show = new Show(
                "title",
                ShowType.CONCERT,
                LocalTime.of(2, 30),
                ""
        );

        given(showRepository.findById(any())).willReturn(Optional.of(show));

        //when
        String updateDescription = "a".repeat(1001);

        //then
        assertThatThrownBy(() -> showService.updateShow(1L, null, updateDescription))
                .isInstanceOf(IllegalArgumentException.class);
    }
}