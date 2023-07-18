package com.programmers.ticketing.dto.seat;

import com.programmers.ticketing.domain.Theater;
import lombok.Getter;

@Getter
public class SeatTheaterDto {
    private final String name;
    private final String address;

    public SeatTheaterDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    static SeatTheaterDto from(Theater theater) {
        String name = theater.getName();
        String address = theater.getAddress();
        return new SeatTheaterDto(name, address);
    }
}
