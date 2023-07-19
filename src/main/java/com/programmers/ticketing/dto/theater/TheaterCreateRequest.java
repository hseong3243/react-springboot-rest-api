package com.programmers.ticketing.dto.theater;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TheaterCreateRequest {
    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 200)
    private String address;

    public TheaterCreateRequest() {
    }

    public TheaterCreateRequest(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
