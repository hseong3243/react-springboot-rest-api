package com.programmers.ticketing.dto.showinformation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.programmers.ticketing.domain.ShowStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowInformationUpdateRequest {
    private ShowStatus showStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    public ShowInformationUpdateRequest() {
    }

    public ShowInformationUpdateRequest(ShowStatus showStatus, LocalDateTime startTime) {
        this.showStatus = showStatus;
        this.startTime = startTime;
    }
}
