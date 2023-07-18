package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r" +
            " join fetch r.showSeat ss" +
            " join fetch ss.seatGrade sg" +
            " join fetch ss.seat s" +
            " join fetch ss.showInformation si" +
            " join fetch si.theater" +
            " join fetch si.show" +
            " where r.email = :email")
    List<Reservation> findAllByEmailWithOthers(@Param("email") String email);

    @Query("select r from Reservation r" +
            " join fetch r.showSeat ss" +
            " join fetch ss.seatGrade sg" +
            " join fetch ss.seat s" +
            " join fetch ss.showInformation si" +
            " join fetch si.theater" +
            " join fetch si.show" +
            " where r.reservationId = :reservationId")
    Optional<Reservation> findByIdWithOthers(@Param("reservationId") Long reservationId);
}
