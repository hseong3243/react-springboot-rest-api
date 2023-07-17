package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Seat;

import java.util.List;

public interface SeatRepositoryCustom {
    List<Seat> findSeats(Long theaterId);
}
