package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.dto.SeatGradeDto;
import com.programmers.ticketing.repository.SeatGradeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SeatGradeServiceTest {
    @InjectMocks
    private SeatGradeService seatGradeService;

    @Mock
    private SeatGradeRepository seatGradeRepository;

    @Test
    @DisplayName("성공: seatGrade 단건 등록")
    void registerSeatGrade() {
        //given
        String name = "seatGrade";

        //when
        seatGradeService.registerSeatGrade(name);

        //then
        then(seatGradeRepository).should().save(any());
    }

    @Test
    @DisplayName("예외: seatGrade 단건 등록 - 범위를 넘은 name")
    void registerSeatGrade_ButNameLengthOutOfRange_Then_Exception() {
        //given
        String name = "a".repeat(51);

        //when
        //then
        assertThatThrownBy(() -> seatGradeService.registerSeatGrade(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: seatGrade 단건 삭제")
    void deleteSeatGrade() {
        //given
        SeatGrade seatGrade = new SeatGrade("seatGrade");

        given(seatGradeRepository.findById(any())).willReturn(Optional.of(seatGrade));

        //when
        seatGradeService.deleteSeatGrade(1L);

        //then
        then(seatGradeRepository).should().delete(any());
    }

    @Test
    @DisplayName("예외: seatGrade 단건 삭제 - 존재하지 않는 seatGrade")
    void deleteSeatGrade_ButNoSuchElement_Then_Exception() {
        //given
        given(seatGradeRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> seatGradeService.deleteSeatGrade(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("성공: seatGrade 목록 조회")
    void findSeatGrades() {
        //given
        SeatGrade seatGradeA = new SeatGrade("seatGradeA");
        SeatGrade seatGradeB = new SeatGrade("seatGradeB");
        List<SeatGrade> seatGrades = List.of(seatGradeA, seatGradeB);

        given(seatGradeRepository.findAll()).willReturn(seatGrades);

        //when
        List<SeatGradeDto> result = seatGradeService.findSeatGrades();

        //then
        SeatGradeDto seatGradeDtoA = SeatGradeDto.from(seatGradeA);
        SeatGradeDto seatGradeDtoB = SeatGradeDto.from(seatGradeB);
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(seatGradeDtoA, seatGradeDtoB);
    }
}