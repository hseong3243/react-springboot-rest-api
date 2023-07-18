package com.programmers.ticketing.dto.seat;

import com.programmers.ticketing.domain.SeatPosition;
import lombok.Getter;

@Getter
public class SeatPositionDto {
    private final int section;
    private final int seatRow;
    private final int seatNumber;

    private SeatPositionDto(int section, int seatRow, int seatNumber) {
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public static SeatPositionDto from(SeatPosition seatPosition) {
        return new SeatPositionDto(
                seatPosition.getSection(),
                seatPosition.getSeatRow(),
                seatPosition.getSeatNumber()
        );
    }
}
