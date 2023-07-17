package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.ShowDto;
import com.programmers.ticketing.dto.TheaterDto;
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
        return new ShowInformationDto(
                showInformation.getShowInformationId(),
                showInformation.getStartTime(),
                showInformation.getShowStatus(),
                showInformation.getCreatedAt()
        );
    }

    public static ShowInformationDto from(ShowInformation showInformation, Show show, Theater theater) {
        ShowDto showDto = ShowDto.from(show);
        TheaterDto theaterDto = TheaterDto.from(theater);
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
