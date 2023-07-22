package com.programmers.ticketing.dto.show;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowsFindRequest {
    private String title = "";

    @Min(0)
    private int page;

    @Min(0)
    private int size;

    public ShowsFindRequest() {
    }
}
