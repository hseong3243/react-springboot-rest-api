package com.programmers.ticketing.dto.reservation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationCreateRequest {
    @NotNull
    private List<Long> showSeatIds;

    @NotNull
    @Email
    @Size(max = 50)
    private String email;

    public ReservationCreateRequest() {
    }

    public ReservationCreateRequest(List<Long> showSeatId, String email) {
        this.showSeatIds = showSeatId;
        this.email = email;
    }
}
