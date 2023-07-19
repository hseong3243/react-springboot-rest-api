package com.programmers.ticketing.dto.showinformation;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import com.programmers.ticketing.domain.Theater;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowInformationDto {
    private final Long showInformationId;
    private final LocalDateTime startTime;
    private final ShowInformationShowDto show;
    private final ShowInformationTheaterDto theater;
    private final ShowStatus showStatus;
    private final LocalDateTime createdAt;

    public ShowInformationDto(Long showInformationId,
                              LocalDateTime startTime,
                              ShowInformationShowDto show,
                              ShowInformationTheaterDto theater,
                              ShowStatus showStatus,
                              LocalDateTime createdAt) {
        this.showInformationId = showInformationId;
        this.startTime = startTime;
        this.show = show;
        this.theater = theater;
        this.showStatus = showStatus;
        this.createdAt = createdAt;
    }

    public static ShowInformationDto from(ShowInformation showInformation) {
        Show show = showInformation.getShow();
        ShowInformationShowDto showDto = ShowInformationShowDto.from(show);
        Theater theater = showInformation.getTheater();
        ShowInformationTheaterDto theaterDto = ShowInformationTheaterDto.from(theater);
        return new ShowInformationDto(
                showInformation.getShowInformationId(),
                showInformation.getStartTime(),
                showDto,
                theaterDto,
                showInformation.getShowStatus(),
                showInformation.getCreatedAt()
        );
    }
}
