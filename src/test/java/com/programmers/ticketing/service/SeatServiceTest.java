package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.SeatDto;
import com.programmers.ticketing.dto.SeatPositionDto;
import com.programmers.ticketing.dto.TheaterDto;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.programmers.ticketing.TicketingTestUtil.createTheater;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @InjectMocks
    private SeatService seatService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @Test
    @DisplayName("성공: seat 단건 등록 기능")
    void registerSeat() {
        //given
        Theater theater = createTheater("theater");

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));
        given(seatRepository.findByTheaterAndSeatPosition(any(), any())).willReturn(Optional.empty());

        //when
        long theaterId = 1L;
        int section = 1;
        int seatRow = 1;
        int seatNumber = 1;
        seatService.registerSeat(theaterId, section, seatRow, seatNumber);

        //then
        then(seatRepository).should().save(any());
    }

    @Test
    @DisplayName("예외: seat 단건 등록 기능 - 존재하지 않는 theater")
    void registerSeat_ButNoSuchTheater_Then_Exception() {
        //given
        given(theaterRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> seatService.registerSeat(1L, 1, 1, 1))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("예외: seat 단건 등록 기능 - 중복된 seat")
    void registerSeat_ButDuplicateSeatExist_Then_Exception() {
        //given
        Theater theater = createTheater("theater");
        SeatPosition seatPosition = new SeatPosition(1, 1, 1);
        Seat seat = new Seat(theater, seatPosition);

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));
        given(seatRepository.findByTheaterAndSeatPosition(any(), any())).willReturn(Optional.of(seat));

        //when
        //then
        assertThatThrownBy(() -> seatService.registerSeat(1L, 1, 1, 1))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("성공: seat 단건 조회 기능")
    void findSeat() {
        //given
        Seat seat = TicketingTestUtil.createSeat("theater", 1);

        given(seatRepository.findSeatWithTheater(any())).willReturn(Optional.of(seat));

        //when
        SeatDto seatDto = seatService.findSeat(1L);

        //then
        TheaterDto theaterDto = TheaterDto.from(seat.getTheater());
        SeatPositionDto seatPositionDto = SeatPositionDto.from(seat.getSeatPosition());
        assertThat(seatDto.getTheaterDto()).usingRecursiveComparison().isEqualTo(theaterDto);
        assertThat(seatDto.getSeatPositionDto()).usingRecursiveComparison().isEqualTo(seatPositionDto);
    }

    @Test
    @DisplayName("예외: seat 단건 조회 기능 - 존재하지 않는 seat")
    void findSeat_ButNoSuchElement_Then_Exception() {
        //given
        given(seatRepository.findSeatWithTheater(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> seatService.findSeat(1L))
                .isInstanceOf(NoSuchElementException.class);
    }
}