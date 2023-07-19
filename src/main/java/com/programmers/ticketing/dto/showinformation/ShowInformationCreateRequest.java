package com.programmers.ticketing.dto.showinformation;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowInformationCreateRequest {
    @NotNull
    private Long showId;

    @NotNull
    private Long theaterId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    public ShowInformationCreateRequest() {
    }

    public ShowInformationCreateRequest(Long showId, Long theaterId, LocalDateTime startTime) {
        this.showId = showId;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }
}
