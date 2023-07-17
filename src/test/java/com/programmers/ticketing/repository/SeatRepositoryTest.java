package com.programmers.ticketing.repository;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Theater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SeatRepositoryTest {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Test
    @DisplayName("성공: seat 단건 조회 - theater 조인")
    void findSeatWithTheater() {
        //given
        Theater theater = TicketingTestUtil.createTheater("theater");
        SeatPosition seatPosition = new SeatPosition(1, 1, 1);
        Seat seat = new Seat(theater, seatPosition);
        theaterRepository.save(theater);
        seatRepository.save(seat);

        //then
        Optional<Seat> seatWithTheater = seatRepository.findSeatWithTheater(seat.getSeatId());

        //then
        assertThat(seatWithTheater).isNotEmpty();
        Seat findSeat = seatWithTheater.get();
        assertThat(findSeat.getTheater()).isEqualTo(theater);
    }
}