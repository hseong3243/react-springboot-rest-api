package com.programmers.ticketing.dto.showseat;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.SeatPosition;
import lombok.Getter;

@Getter
public class ShowSeatSeatDto {
    private final int section;
    private final int seatRow;
    private final int seatNumber;
    private final String seatGrade;

    private ShowSeatSeatDto(int section, int seatRow, int seatNumber, String seatGrade) {
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatGrade = seatGrade;
    }

    public static ShowSeatSeatDto from(Seat seat, SeatGrade seatGrade) {
        SeatPosition seatPosition = seat.getSeatPosition();
        String seatGradeName = seatGrade.getName();
        int section = seatPosition.getSection();
        int seatRow = seatPosition.getSeatRow();
        int seatNumber = seatPosition.getSeatNumber();
        return new ShowSeatSeatDto(section, seatRow, seatNumber, seatGradeName);
    }
}
