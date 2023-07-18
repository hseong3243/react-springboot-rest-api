package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationShowDto {
    private final String title;
    private final LocalTime playTime;
    private final String description;
    private final ShowStatus showStatus;
    private final LocalDateTime startTime;

    private ReservationShowDto(String title, LocalTime playTime, String description, ShowStatus showStatus, LocalDateTime startTime) {
        this.title = title;
        this.playTime = playTime;
        this.description = description;
        this.showStatus = showStatus;
        this.startTime = startTime;
    }

    static ReservationShowDto from(Show show, ShowInformation showInformation) {
        String title = show.getTitle();
        LocalTime playtime = show.getPlaytime();
        String description = show.getDescription();
        ShowStatus showStatus = showInformation.getShowStatus();
        LocalDateTime startTime = showInformation.getStartTime();
        return new ReservationShowDto(title, playtime, description, showStatus, startTime);
    }
}
