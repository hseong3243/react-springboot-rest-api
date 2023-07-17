package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {
    Optional<Seat> findByTheaterAndSeatPosition(Theater theater, SeatPosition position);

    @Query("select s from Seat s join fetch s.theater t where s.seatId = :seatId")
    Optional<Seat> findSeatWithTheater(@Param("seatId") Long seatId);
}
