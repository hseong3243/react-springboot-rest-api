package com.programmers.ticketing.dto.showseat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowSeatBulkCreateRequest {
    @NotNull
    private Long showInformationId;

    @NotNull
    private Long seatGradeId;

    private List<Long> seatIds;

    @Positive
    private int fee;

    public ShowSeatBulkCreateRequest() {
    }

    public ShowSeatBulkCreateRequest(Long showInformationId, Long seatGradeId, List<Long> seatIds, int fee) {
        this.showInformationId = showInformationId;
        this.seatGradeId = seatGradeId;
        this.seatIds = seatIds;
        this.fee = fee;
    }
}
