package com.programmers.ticketing.dto.showinformation;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class ShowInformationsFindRequest {
    private final Long showId;
    private final Long theaterId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startTime;

    public ShowInformationsFindRequest(Long showId, Long theaterId, LocalDateTime startTime) {
        this.showId = showId;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }
}
