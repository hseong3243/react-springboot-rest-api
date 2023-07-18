package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.ShowSeat;
import lombok.Getter;

@Getter
public class ShowSeatDto {
    private final Long showSeatId;
    private final ShowSeatSeatDto showSeatSeatDto;
    private final SeatGradeDto seatGrade;
    private final int fee;

    private ShowSeatDto(Long showSeatId,
                        ShowSeatSeatDto showSeatSeatDto,
                        SeatGradeDto seatGradeDto,
                        int fee) {
        this.showSeatId = showSeatId;
        this.showSeatSeatDto = showSeatSeatDto;
        this.seatGrade = seatGradeDto;
        this.fee = fee;
    }

    public static ShowSeatDto from(ShowSeat showSeat) {
        Seat seat = showSeat.getSeat();
        SeatGrade seatGrade = showSeat.getSeatGrade();
        ShowSeatSeatDto showSeatSeatDto = ShowSeatSeatDto.from(seat);
        SeatGradeDto seatGradeDto = SeatGradeDto.from(seatGrade);
        return new ShowSeatDto(
                showSeat.getShowSeatId(),
                showSeatSeatDto,
                seatGradeDto,
                showSeat.getFee()
        );
    }
}
