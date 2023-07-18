package com.programmers.ticketing.service;

import com.programmers.ticketing.repository.SeatGradeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
}