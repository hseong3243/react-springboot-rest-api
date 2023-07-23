package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.show.ShowDto;
import com.programmers.ticketing.repository.ShowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        showService.registerShow(title, showType, playtime, description, null);

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
        assertThatThrownBy(() -> showService.registerShow(title, showType, playtime, description, null))
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
        Show show = new Show(title, showType, playtime, description, null);

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
        int page = 0;
        int size = 2;
        Show showA = new Show("titleA", ShowType.CONCERT, LocalTime.of(2, 30), "", null);
        Show showB = new Show("titleB", ShowType.CONCERT, LocalTime.of(2, 30), "", null);
        List<Show> shows = List.of(showA, showB);
        PageRequest pageRequest = PageRequest.of(page, size);
        PageImpl<Show> showsWithPage = new PageImpl<>(shows, pageRequest, 2);

        given(showRepository.findAllByTitleContaining(any(), any())).willReturn(showsWithPage);

        //when
        Page<ShowDto> result = showService.findShows("", page, size);

        //then
        ShowDto showDtoA = ShowDto.from(showA);
        ShowDto showDtoB = ShowDto.from(showB);
        assertThat(result.getContent()).usingRecursiveFieldByFieldElementComparator()
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
                "",
                null);

        given(showRepository.findById(any())).willReturn(Optional.of(show));

        //when
        LocalTime updatePlaytime = LocalTime.of(2, 0);
        String updateDescription = "new description";
        showService.updateShow(1L, updatePlaytime, updateDescription, null);

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
                "",
                null);

        given(showRepository.findById(any())).willReturn(Optional.of(show));

        //when
        String updateDescription = "a".repeat(1001);

        //then
        assertThatThrownBy(() -> showService.updateShow(1L, null, updateDescription, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: show 단건 삭제 기능")
    void deleteShow() {
        //given
        Show show = new Show(
                "title",
                ShowType.CONCERT,
                LocalTime.of(2, 30),
                "",
                null);

        //when
        showRepository.delete(show);

        //then
        then(showRepository).should().delete(any());
    }

    @Test
    @DisplayName("예외: show 단건 삭제 기능 - 존재하지 않는 show")
    void deleteShow_ButNoSuchElement_Then_Exception() {
        //given
        given(showRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> showService.deleteShow(any()))
                .isInstanceOf(NoSuchElementException.class);
    }
}