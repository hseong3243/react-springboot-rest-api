package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
