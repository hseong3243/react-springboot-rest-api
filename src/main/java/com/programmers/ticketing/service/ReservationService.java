package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.repository.ShowSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
}
