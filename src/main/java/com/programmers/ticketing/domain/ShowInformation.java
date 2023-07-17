package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class ShowInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showInformationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ShowStatus showStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;


    public ShowInformation() {

    }

    public ShowInformation(Show show, Theater theater, LocalDateTime startTime) {
        nullCheck(startTime);
        validateStartTime(startTime);
        this.show = show;
        this.theater = theater;
        this.startTime = startTime;
        this.showStatus = ShowStatus.BEFORE;
        this.createdAt = LocalDateTime.now();
    }

    private void nullCheck(LocalDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Show information start time must not be null");
        }
    }

    private void validateStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Show information start time must be after now");
        }
    }

    public void update(ShowStatus showStatus, LocalDateTime startTime) {
        if (showStatus != null) {
            this.showStatus = showStatus;
        }
        if (startTime != null) {
            validateStartTime(startTime);
            this.startTime = startTime;
        }
    }
}
