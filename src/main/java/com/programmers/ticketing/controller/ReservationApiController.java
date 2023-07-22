package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.reservation.ReservationCreateRequest;
import com.programmers.ticketing.dto.reservation.ReservationDto;
import com.programmers.ticketing.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReservationApiController {
    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<Result<List<ReservationDto>>> findReservations(@RequestParam("email") String email) {
        List<ReservationDto> reservations = reservationService.findReservations(email);
        return ResponseEntity.ok().body(new Result<>(reservations));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Result<List<Long>>> createReservation(@RequestBody @Valid ReservationCreateRequest request) {
        List<Long> reservationIds = reservationService.createReservation(request.getShowSeatIds(), request.getEmail());
        URI location = URI.create("/api/v1/reservations");
        return ResponseEntity.created(location).body(new Result<>(reservationIds));
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<Result<ReservationDto>> findReservation(@PathVariable("reservationId") Long reservationId) {
        ReservationDto reservation = reservationService.findReservation(reservationId);
        return ResponseEntity.ok().body(new Result<>(reservation));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reservations/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
