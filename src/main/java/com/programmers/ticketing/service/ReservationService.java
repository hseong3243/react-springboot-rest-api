package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.ReservationDto;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShowSeatRepository showSeatRepository;

    public ReservationService(ReservationRepository reservationRepository, ShowSeatRepository showSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Transactional
    public Long createReservation(Long showSeatId, String email) {
        ShowSeat showSeat = showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new NoSuchElementException("No such show seat exist"));

        Reservation reservation = new Reservation(showSeat, email);
        reservationRepository.save(reservation);
        return reservation.getReservationId();
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> findReservations(String email) {
        List<Reservation> reservations = reservationRepository.findAllByEmailWithOthers(email);
        return reservations.stream()
                .map(ReservationDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservationDto findReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdWithOthers(reservationId)
                .orElseThrow(() -> new NoSuchElementException("No such reservation exist"));
        return ReservationDto.from(reservation);
    }
}
