package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.SeatGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {
    Optional<SeatGrade> findByName(String name);
}
