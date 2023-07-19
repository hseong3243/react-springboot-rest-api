package com.programmers.ticketing.dto.theater;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TheaterUpdateRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 200)
    private String address;

    public TheaterUpdateRequest() {
    }

    public TheaterUpdateRequest(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
