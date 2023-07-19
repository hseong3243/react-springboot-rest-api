package com.programmers.ticketing.dto.showseat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ShowSeatCreateRequest {
    @NotNull
    private Long showInformationId;

    @NotNull
    private Long seatGradeId;

    @NotNull
    private Long seatId;

    @Positive
    private int fee;

    public ShowSeatCreateRequest() {
    }

    public ShowSeatCreateRequest(Long showInformationId, Long seatGradeId, Long seatId, int fee) {
        this.showInformationId = showInformationId;
        this.seatGradeId = seatGradeId;
        this.seatId = seatId;
        this.fee = fee;
    }
}
