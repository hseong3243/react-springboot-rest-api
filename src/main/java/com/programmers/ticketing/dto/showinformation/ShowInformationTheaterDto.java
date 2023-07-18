package com.programmers.ticketing.dto.showinformation;

import com.programmers.ticketing.domain.Theater;
import lombok.Getter;

@Getter
public class ShowInformationTheaterDto {
    private final String name;
    private final String address;

    public ShowInformationTheaterDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    static ShowInformationTheaterDto from(Theater theater) {
        String name = theater.getName();
        String address = theater.getAddress();
        return new ShowInformationTheaterDto(name, address);
    }
}
