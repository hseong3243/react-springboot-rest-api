package com.programmers.ticketing.dto.show;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ShowUpdateRequest {
    private LocalTime playtime;

    @Size(max = 1000)
    private String description;

    public ShowUpdateRequest() {
    }

    public ShowUpdateRequest(LocalTime playtime, String description) {
        this.playtime = playtime;
        this.description = description;
    }
}
