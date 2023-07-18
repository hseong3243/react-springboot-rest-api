package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.repository.SeatGradeRepository;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShowSeatServiceTest {
    @InjectMocks
    private ShowSeatService showSeatService;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private ShowInformationRepository showInformationRepository;

    @Mock
    private SeatGradeRepository seatGradeRepository;

    @Mock
    private SeatRepository seatRepository;

    @Test
    @DisplayName("성공: showSeat 단건 등록")
    void registerShowSeat() {
        //given
        ShowSeat showSeat = TicketingTestUtil.createShowSeat("vip", 100);
        ShowInformation showInformation = showSeat.getShowInformation();
        SeatGrade seatGrade = showSeat.getSeatGrade();
        Seat seat = showSeat.getSeat();

        given(showInformationRepository.findById(any())).willReturn(Optional.ofNullable(showInformation));
        given(seatGradeRepository.findById(any())).willReturn(Optional.ofNullable(seatGrade));
        given(seatRepository.findById(any())).willReturn(Optional.ofNullable(seat));

        //when
        showSeatService.registerShowSeat(1L, 1L, 1L, 100);

        //then
        then(showSeatRepository).should().save(any());
    }
}