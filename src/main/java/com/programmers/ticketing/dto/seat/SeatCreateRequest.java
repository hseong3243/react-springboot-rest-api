package com.programmers.ticketing.dto.seat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SeatCreateRequest {
    @NotNull
    private Long theaterId;

    @Positive
    private int section;

    @Positive
    private int seatRow;

    @Positive
    private int seatNumber;

    public SeatCreateRequest() {
    }

    public SeatCreateRequest(Long theaterId, int section, int seatRow, int seatNumber) {
        this.theaterId = theaterId;
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }
}