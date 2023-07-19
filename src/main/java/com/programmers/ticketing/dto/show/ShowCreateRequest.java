package com.programmers.ticketing.dto.show;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.programmers.ticketing.domain.ShowType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ShowCreateRequest {
    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    private ShowType showType;

    @NotNull
    private LocalTime playtime;

    @Size(max = 1000)
    private String description;

    public ShowCreateRequest() {
    }

    public ShowCreateRequest(String title, ShowType showType, LocalTime playtime, String description) {
        this.title = title;
        this.showType = showType;
        this.playtime = playtime;
        this.description = description;
    }
}
