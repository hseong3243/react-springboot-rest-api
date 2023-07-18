package com.programmers.ticketing.dto;

import com.programmers.ticketing.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDto {
    private final Long reservationId;
    private final ReservationTheaterDto theater;
    private final ReservationSeatDto seat;
    private final ReservationShowDto show;
    private final int fee;
    private final String email;
    private final LocalDateTime createdAt;

    public ReservationDto(Long reservationId,
                          ReservationTheaterDto theater, ReservationSeatDto seat,
                          ReservationShowDto show,
                          int fee, String email,
                          LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.theater = theater;
        this.seat = seat;
        this.show = show;
        this.fee = fee;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static ReservationDto from(Reservation reservation) {
        ShowSeat showSeat = reservation.getShowSeat();
        SeatGrade seatGrade = showSeat.getSeatGrade();
        Seat seat = showSeat.getSeat();
        ShowInformation showInformation = showSeat.getShowInformation();
        Show show = showInformation.getShow();
        Theater theater = showInformation.getTheater();

        ReservationShowDto reservationShowDto = ReservationShowDto.from(show, showInformation);
        ReservationSeatDto reservationSeatDto = ReservationSeatDto.from(seat, seatGrade);
        ReservationTheaterDto reservationTheaterDto = ReservationTheaterDto.from(theater);
        return new ReservationDto(
                reservation.getReservationId(),
                reservationTheaterDto,
                reservationSeatDto,
                reservationShowDto,
                showSeat.getFee(),
                reservation.getEmail(),
                reservation.getCreatedAt()
        );
    }
}
