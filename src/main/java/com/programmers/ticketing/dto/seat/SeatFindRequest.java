package com.programmers.ticketing.dto.seat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SeatFindRequest {
    @NotNull
    private final Long theaterId;

    public SeatFindRequest(Long theaterId) {
        this.theaterId = theaterId;
    }
}
