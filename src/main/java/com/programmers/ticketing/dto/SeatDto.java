package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Seat;
import lombok.Getter;

@Getter
public class SeatDto {
    private final Long seatId;
    private final TheaterDto theaterDto;
    private final SeatPositionDto seatPositionDto;

    private SeatDto(Long seatId, TheaterDto theaterDto, SeatPositionDto seatPositionDto) {
        this.seatId = seatId;
        this.theaterDto = theaterDto;
        this.seatPositionDto = seatPositionDto;
    }

    public static SeatDto from(Seat seat) {
        TheaterDto theaterDto = TheaterDto.from(seat.getTheater());
        SeatPositionDto seatPositionDto = SeatPositionDto.from(seat.getSeatPosition());
        return new SeatDto(seat.getSeatId(), theaterDto, seatPositionDto);
    }
}
