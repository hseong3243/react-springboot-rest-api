package com.programmers.ticketing.dto.reservation;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.SeatPosition;
import lombok.Getter;

@Getter
public class ReservationSeatDto {
    private final int section;
    private final int seatRow;
    private final int seatNumber;
    private final String seatGrade;

    private ReservationSeatDto(int section, int seatRow, int seatNumber, String seatGrade) {
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatGrade = seatGrade;
    }

    static ReservationSeatDto from(Seat seat, SeatGrade seatGrade) {
        SeatPosition seatPosition = seat.getSeatPosition();
        int section = seatPosition.getSection();
        int seatRow = seatPosition.getSeatRow();
        int seatNumber = seatPosition.getSeatNumber();
        String seatGradeName = seatGrade.getName();
        return new ReservationSeatDto(section, seatRow, seatNumber, seatGradeName);
    }
}
