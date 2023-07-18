package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.reservation.ReservationDto;
import com.programmers.ticketing.repository.ReservationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
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

    @Test
    @DisplayName("성공: reservation 단건 조회")
    void findReservation() {
        //given
        Reservation reservation = TicketingTestUtil.createReservation();

        given(reservationRepository.findByIdWithOthers(any())).willReturn(Optional.of(reservation));

        //when
        ReservationDto reservationDto = reservationService.findReservation(1L);

        //then
        assertThat(reservationDto.getEmail()).isEqualTo(reservation.getEmail());
        assertThat(reservationDto.getShow()).isNotNull();
        assertThat(reservationDto.getSeat()).isNotNull();
        assertThat(reservationDto.getTheater()).isNotNull();
    }

    @Test
    @DisplayName("예외: reservation 단건 조회 - 존재하지 않는 reservation")
    void findReservation_ButNoSuchElement_Then_Exception() {
        //given
        given(reservationRepository.findByIdWithOthers(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> reservationService.findReservation(any()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("성공: reservation 단건 삭제")
    void deleteReservation() {
        //given
        Reservation reservation = TicketingTestUtil.createReservation();

        given(reservationRepository.findById(any())).willReturn(Optional.of(reservation));

        //when
        reservationService.deleteReservation(1L);

        //then
        then(reservationRepository).should().delete(any());
    }

    @Test
    @DisplayName("예외: reservation 단건 삭제 - 존재하지 않는 reservation")
    void deleteReservation_ButNoSuchElement_Then_Exception() {
        //given
        given(reservationRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> reservationService.deleteReservation(1L))
                .isInstanceOf(NoSuchElementException.class);
    }
}