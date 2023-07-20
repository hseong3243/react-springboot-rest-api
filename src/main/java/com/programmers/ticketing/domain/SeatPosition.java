package com.programmers.ticketing.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class SeatPosition {
    private int section;
    private int seatRow;
    private int seatNumber;

    public SeatPosition() {

    }

    public SeatPosition(int section, int seatRow, int seatNumber) {
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }
}
