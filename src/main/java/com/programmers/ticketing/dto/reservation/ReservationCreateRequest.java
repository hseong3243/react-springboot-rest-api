package com.programmers.ticketing.dto.reservation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReservationCreateRequest {
    @NotNull
    private Long showSeatId;

    @NotNull
    @Email
    @Size(max = 50)
    private String email;

    public ReservationCreateRequest() {
    }

    public ReservationCreateRequest(Long showSeatId, String email) {
        this.showSeatId = showSeatId;
        this.email = email;
    }
}
