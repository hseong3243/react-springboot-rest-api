package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Reservation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.reservation.ReservationDto;
import com.programmers.ticketing.repository.ReservationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Long> createReservation(List<Long> showSeatIds, String email) {
        showSeatRepository.findAllReservedShowSeatByShowSeatIds(showSeatIds)
                .forEach(showSeat -> {
                    throw new DuplicateKeyException("Selected seat already reserved");
                });

        List<ShowSeat> notReservedShowSeats = showSeatRepository.findAllNotReservedShowSeatByShowSeatIds(showSeatIds);
        List<Reservation> reservations = notReservedShowSeats.stream()
                .map(showSeat -> new Reservation(showSeat, email))
                .toList();
        reservationRepository.saveAll(reservations);
        return reservations.stream()
                .map(Reservation::getReservationId)
                .toList();
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

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("No such reservation exist"));
        reservationRepository.delete(reservation);
    }
}
