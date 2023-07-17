package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.SeatDto;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final TheaterRepository theaterRepository;

    public SeatService(SeatRepository seatRepository, TheaterRepository theaterRepository) {
        this.seatRepository = seatRepository;
        this.theaterRepository = theaterRepository;
    }

    @Transactional
    public Long registerSeat(Long theaterId, int section, int seatRow, int seatNumber) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such theater exist");
                });

        SeatPosition seatPosition = new SeatPosition(section, seatRow, seatNumber);
        seatRepository.findByTheaterAndSeatPosition(theater, seatPosition)
                .ifPresent(seat -> {
                    log.warn("Duplicate seat already exist - SeatId: {}", seat.getSeatId());
                    throw new DuplicateKeyException("Duplicate seat already exist");
                });

        Seat seat = new Seat(theater, seatPosition);
        seatRepository.save(seat);
        return seat.getSeatId();
    }

    @Transactional(readOnly = true)
    public SeatDto findSeat(Long seatId) {
        Seat seat = seatRepository.findSeatWithTheater(seatId)
                .orElseThrow(() -> {
                    log.warn("No such seat exist - SeatId: {}", seatId);
                    return new NoSuchElementException("No such seat exist");
                });
        return SeatDto.from(seat);
    }

    @Transactional(readOnly = true)
    public List<SeatDto> findSeats(Long theaterId) {
        List<Seat> seats = seatRepository.findSeats(theaterId);
        return seats.stream()
                .map(SeatDto::from)
                .toList();
    }

    @Transactional
    public void deleteSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> {
                    log.warn("No such seat exist - SeatId: {}", seatId);
                    return new NoSuchElementException("No such seat exist");
                });
        seatRepository.delete(seat);
    }
}
