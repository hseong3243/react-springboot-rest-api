package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatPosition;
import lombok.Getter;

@Getter
public class ShowSeatSeatDto {
    private final Long seatId;
    private final SeatPositionDto seatPositionDto;

    private ShowSeatSeatDto(Long seatId, SeatPositionDto seatPositionDto) {
        this.seatId = seatId;
        this.seatPositionDto = seatPositionDto;
    }

    public static ShowSeatSeatDto from(Seat seat) {
        SeatPosition seatPosition = seat.getSeatPosition();
        SeatPositionDto seatPositionDto = SeatPositionDto.from(seatPosition);
        return new ShowSeatSeatDto(seat.getSeatId(), seatPositionDto);
    }
}
