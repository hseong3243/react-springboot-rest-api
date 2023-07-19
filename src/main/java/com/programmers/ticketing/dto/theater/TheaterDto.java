package com.programmers.ticketing.dto.theater;

import com.programmers.ticketing.domain.Theater;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterDto {
    private Long theaterId;
    private String name;
    private String address;

    private TheaterDto(Long theaterId, String name, String address) {
        this.theaterId = theaterId;
        this.name = name;
        this.address = address;
    }

    public static TheaterDto from(Theater theater) {
        return new TheaterDto(
                theater.getTheaterId(),
                theater.getName(),
                theater.getAddress()
        );
    }
}
