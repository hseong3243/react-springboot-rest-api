package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
}
