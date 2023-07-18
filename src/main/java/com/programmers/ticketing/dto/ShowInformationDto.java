package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ShowInformationDto {
    private Long showInformationId;
    private ShowDto showDto;
    private TheaterDto theaterDto;
    private LocalDateTime startTime;
    private ShowStatus showStatus;
    private LocalDateTime createdAt;

    public ShowInformationDto(
            Long showInformationId,
            LocalDateTime startTime,
            ShowStatus showStatus,
            LocalDateTime createdAt
    ) {
        this(showInformationId, null, null, startTime, showStatus, createdAt);
    }

    public ShowInformationDto(
            Long showInformationId,
            ShowDto showDto,
            TheaterDto theaterDto,
            LocalDateTime startTime,
            ShowStatus showStatus,
            LocalDateTime createdAt
    ) {
        this.showInformationId = showInformationId;
        this.showDto = showDto;
        this.theaterDto = theaterDto;
        this.startTime = startTime;
        this.showStatus = showStatus;
        this.createdAt = createdAt;
    }

    public static ShowInformationDto from(ShowInformation showInformation) {
        ShowDto showDto = ShowDto.from(showInformation.getShow());
        TheaterDto theaterDto = TheaterDto.from(showInformation.getTheater());
        return new ShowInformationDto(
                showInformation.getShowInformationId(),
                showDto,
                theaterDto,
                showInformation.getStartTime(),
                showInformation.getShowStatus(),
                showInformation.getCreatedAt()
        );
    }
}
