package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Test
    @DisplayName("성공: reservation 생성 기능")
    void createReservation() {
        //given
        ShowSeat showSeat = TicketingTestUtil.createShowSeat();

        given(showSeatRepository.findById(any())).willReturn(Optional.of(showSeat));

        //when
        reservationService.createReservation(1L, "email");

        //then
        then(reservationRepository).should().save(any());
    }
}