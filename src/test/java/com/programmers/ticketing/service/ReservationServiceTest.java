package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.ReservationDto;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("성공: reservation 목록 조회")
    void findReservations() {
        //given
        List<Reservation> reservations = TicketingTestUtil.createReservations(10);

        given(reservationRepository.findAllByEmailWithOthers(any())).willReturn(reservations);

        //when
        List<ReservationDto> result = reservationService.findReservations("email");

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(reservations.size());
    }
}