package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.repository.SeatGradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatGradeService {
    private final SeatGradeRepository seatGradeRepository;

    public SeatGradeService(SeatGradeRepository seatGradeRepository) {
        this.seatGradeRepository = seatGradeRepository;
    }

    @Transactional
    public Long registerSeatGrade(String name) {
        SeatGrade seatGrade = new SeatGrade(name);
        seatGradeRepository.save(seatGrade);
        return seatGrade.getSeatGradeId();
    }
}
