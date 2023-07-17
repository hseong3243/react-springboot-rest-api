package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByTheaterAndSeatPosition(Theater theater, SeatPosition position);
}
