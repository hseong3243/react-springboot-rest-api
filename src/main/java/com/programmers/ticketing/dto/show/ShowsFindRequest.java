package com.programmers.ticketing.dto.show;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ShowsFindRequest {
    @Min(0)
    private final int page;

    @Min(0)
    private final int size;

    public ShowsFindRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
