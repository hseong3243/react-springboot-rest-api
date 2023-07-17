package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
