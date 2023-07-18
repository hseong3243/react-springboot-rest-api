package com.programmers.ticketing.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_information_id", nullable = false)
    private ShowInformation showInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_grade_id", nullable = false)
    private SeatGrade seatGrade;

    private int fee;

    public ShowSeat(ShowInformation showInformation, Seat seat, SeatGrade seatGrade, int fee) {
        this.showInformation = showInformation;
        this.seat = seat;
        this.seatGrade = seatGrade;
        this.fee = fee;
    }
}
