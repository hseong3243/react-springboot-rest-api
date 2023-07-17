package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Embedded
    @Column(nullable = false)
    private SeatPosition seatPosition;

    public Seat() {
    }

    public Seat(Theater theater, SeatPosition seatPosition) {
        this.theater = theater;
        this.seatPosition = seatPosition;
    }
}
