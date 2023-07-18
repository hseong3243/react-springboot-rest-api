package com.programmers.ticketing.dto.seat;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Theater;
import lombok.Getter;

@Getter
public class SeatDto {
    private final Long seatId;
    private final SeatTheaterDto theater;
    private final SeatPositionDto position;

    private SeatDto(Long seatId, SeatTheaterDto seatTheaterDto, SeatPositionDto seatPositionDto) {
        this.seatId = seatId;
        this.theater = seatTheaterDto;
        this.position = seatPositionDto;
    }

    public static SeatDto from(Seat seat) {
        Theater theater = seat.getTheater();
        SeatPosition seatPosition = seat.getSeatPosition();
        SeatTheaterDto theaterDto = SeatTheaterDto.from(theater);
        SeatPositionDto seatPositionDto = SeatPositionDto.from(seatPosition);
        return new SeatDto(seat.getSeatId(), theaterDto, seatPositionDto);
    }
}
