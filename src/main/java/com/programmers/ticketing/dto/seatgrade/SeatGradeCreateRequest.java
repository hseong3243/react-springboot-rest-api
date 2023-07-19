package com.programmers.ticketing.dto.seatgrade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SeatGradeCreateRequest {
    @NotNull
    @Size(max = 50)
    private String name;

    public SeatGradeCreateRequest() {
    }

    public SeatGradeCreateRequest(String name) {
        this.name = name;
    }
}
