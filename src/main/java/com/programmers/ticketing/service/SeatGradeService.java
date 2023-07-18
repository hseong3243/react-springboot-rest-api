package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.dto.SeatGradeDto;
import com.programmers.ticketing.repository.SeatGradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
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

    @Transactional(readOnly = true)
    public List<SeatGradeDto> findSeatGrades() {
        List<SeatGrade> seatGrades = seatGradeRepository.findAll();
        return seatGrades.stream()
                .map(SeatGradeDto::from)
                .toList();
    }

    @Transactional
    public void deleteSeatGrade(Long seatGradeId) {
        SeatGrade seatGrade = seatGradeRepository.findById(seatGradeId)
                .orElseThrow(() -> {
                    log.warn("No such seat grade exist - SeatGradeId: {}", seatGradeId);
                    return new NoSuchElementException("No such seat grade exist");
                });
        seatGradeRepository.delete(seatGrade);
    }
}
