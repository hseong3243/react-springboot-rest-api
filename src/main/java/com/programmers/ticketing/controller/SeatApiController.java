package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.seat.SeatCreateRequest;
import com.programmers.ticketing.dto.seat.SeatDto;
import com.programmers.ticketing.dto.seat.SeatFindRequest;
import com.programmers.ticketing.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SeatApiController {
    private final SeatService seatService;

    public SeatApiController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    public ResponseEntity<Result<List<SeatDto>>> findSeats(@ModelAttribute @Valid SeatFindRequest request) {
        List<SeatDto> seats = seatService.findSeats(request.getTheaterId());
        return ResponseEntity.ok().body(new Result<>(seats));
    }

    @PostMapping("/seats")
    public ResponseEntity<Result<Long>> registerSeat(@RequestBody @Valid SeatCreateRequest request) {
        Long seatId = seatService.registerSeat(
                request.getTheaterId(),
                request.getSection(),
                request.getSeatRow(),
                request.getSeatNumber());
        URI location = URI.create("/api/v1/seats/" + seatId);
        return ResponseEntity.created(location).body(new Result<>(seatId));
    }

    @GetMapping("/seats/{seatId}")
    public ResponseEntity<Result<SeatDto>> findSeat(@PathVariable("seatId") Long seatId) {
        SeatDto seat = seatService.findSeat(seatId);
        return ResponseEntity.ok().body(new Result<>(seat));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/seats/{seatId}")
    public void deleteSeat(@PathVariable("seatId") Long seatId) {
        seatService.deleteSeat(seatId);
    }
}
