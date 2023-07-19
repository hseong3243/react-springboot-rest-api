package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowSeat;
import com.programmers.ticketing.dto.showseat.ShowSeatDto;
import com.programmers.ticketing.repository.SeatGradeRepository;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public List<Long> registerMultipleShowSeat(Long showInformationId, Long seatGradeId, List<Long> seatIds, int fee){
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

        List<Seat> seats = seatRepository.findAllBySeatIdIn(seatIds);
        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> new ShowSeat(showInformation, seat, seatGrade, fee))
                .toList();
        showSeatRepository.saveAll(showSeats);
        return showSeats.stream()
                .map(ShowSeat::getShowSeatId)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShowSeatDto> findShowSeats(Long showInformationId) {
        ShowInformation showInformation = showInformationRepository.findById(showInformationId)
                .orElseThrow(() -> {
                    log.warn("No such show information exist - ShowInformationId: {}", showInformationId);
                    return new NoSuchElementException("No such show information exist");
                });

        List<ShowSeat> showSeats = showSeatRepository.findAllByShowInformationWithShowInformationAndSeatAndSeatGrade(showInformation);
        return showSeats.stream()
                .map(ShowSeatDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ShowSeatDto findShowSeat(Long showSeatId) {
        ShowSeat showSeat = showSeatRepository.findByIdWithSeatAndSeatGrade(showSeatId)
                .orElseThrow(() -> new NoSuchElementException("No such show seat exist"));
        return ShowSeatDto.from(showSeat);
    }
}
