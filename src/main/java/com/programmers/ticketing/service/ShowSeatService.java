package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.repository.SeatGradeRepository;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class ShowSeatService {
    private final ShowSeatRepository showSeatRepository;
    private final ShowInformationRepository showInformationRepository;
    private final SeatGradeRepository seatGradeRepository;
    private final SeatRepository seatRepository;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowInformationRepository showInformationRepository, SeatGradeRepository seatGradeRepository, SeatRepository seatRepository) {
        this.showSeatRepository = showSeatRepository;
        this.showInformationRepository = showInformationRepository;
        this.seatGradeRepository = seatGradeRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Long registerShowSeat(Long showInformationId, Long seatGradeId, Long seatId, int fee) {
        ShowInformation showInformation = showInformationRepository.findById(showInformationId)
                .orElseThrow(() -> {
                    log.warn("No such show information exist - ShowInformationId: {}", showInformationId);
                    return new NoSuchElementException("No such show information exist");
                });
        SeatGrade seatGrade = seatGradeRepository.findById(seatGradeId)
                .orElseThrow(() -> {
                    log.warn("No such seat grade exist - SeatGradeId: {}", seatGradeId);
                    return new NoSuchElementException("No such seat grade exist");
                });
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> {
                    log.warn("No such seat exist - SeatId: {}", seatId);
                    return new NoSuchElementException("No such seat exist");
                });

        ShowSeat showSeat = new ShowSeat(showInformation, seat, seatGrade, fee);
        showSeatRepository.save(showSeat);
        return showSeat.getShowSeatId();
    }
}
