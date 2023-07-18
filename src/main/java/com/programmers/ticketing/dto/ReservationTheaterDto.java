package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Theater;
import lombok.Getter;

@Getter
public class ReservationTheaterDto {
    private final String name;
    private final String address;

    private ReservationTheaterDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    static ReservationTheaterDto from(Theater theater) {
        String name = theater.getName();
        String address = theater.getAddress();
        return new ReservationTheaterDto(name, address);
    }
}
