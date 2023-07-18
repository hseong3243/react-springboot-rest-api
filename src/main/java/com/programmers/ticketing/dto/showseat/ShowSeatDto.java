package com.programmers.ticketing.dto.showseat;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.SeatGrade;
import com.programmers.ticketing.domain.ShowSeat;
import lombok.Getter;

@Getter
public class ShowSeatDto {
    private final Long showSeatId;
    private final ShowSeatSeatDto seat;
    private final int fee;

    private ShowSeatDto(Long showSeatId,
                        ShowSeatSeatDto showSeatSeatDto,
                        int fee) {
        this.showSeatId = showSeatId;
        this.seat = showSeatSeatDto;
        this.fee = fee;
    }

    public static ShowSeatDto from(ShowSeat showSeat) {
        Seat seat = showSeat.getSeat();
        SeatGrade seatGrade = showSeat.getSeatGrade();
        ShowSeatSeatDto seatDto = ShowSeatSeatDto.from(seat, seatGrade);
        return new ShowSeatDto(
                showSeat.getShowSeatId(),
                seatDto,
                showSeat.getFee()
        );
    }
}
