package com.programmers.ticketing.dto.showinformation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.programmers.ticketing.domain.ShowStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowInformationUpdateRequest {
    @NotNull
    private Long showInformationId;

    private ShowStatus showStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    public ShowInformationUpdateRequest() {
    }

    public ShowInformationUpdateRequest(Long showInformationId, ShowStatus showStatus, LocalDateTime startTime) {
        this.showInformationId = showInformationId;
        this.showStatus = showStatus;
        this.startTime = startTime;
    }
}
