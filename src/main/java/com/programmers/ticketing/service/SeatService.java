package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.seat.SeatDto;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Long> registerSeats(Long theaterId, int section, int seatRow, int seatNumber) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such theater exist");
                });

        SeatPosition seatCreatesUnderPosition = new SeatPosition(section, seatRow, seatNumber);
        List<Seat> noneDuplicateSeats = createNoneDuplicateSeats(theater, seatCreatesUnderPosition);
        seatRepository.saveAll(noneDuplicateSeats);

        return noneDuplicateSeats.stream()
                .map(Seat::getSeatId)
                .toList();
    }

    private List<Seat> createNoneDuplicateSeats(Theater theater, SeatPosition seatCreatesUnderPosition) {
        int section = seatCreatesUnderPosition.getSection();
        int seatRow = seatCreatesUnderPosition.getSeatRow();
        int seatNumber = seatCreatesUnderPosition.getSeatNumber();

        List<Seat> duplicateSeats = seatRepository.findAllByTheaterAndSection(theater, section);
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= seatRow; row++) {
            for (int number = 1; number <= seatNumber; number++) {
                SeatPosition seatPosition = new SeatPosition(section, row, number);
                boolean duplicate = duplicateSeats.stream()
                        .anyMatch(seat -> seat.isDuplicate(seatPosition));
                if (duplicate) {
                    continue;
                }

                Seat seat = new Seat(theater, seatPosition);
                seats.add(seat);
            }
        }
        return seats;
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
