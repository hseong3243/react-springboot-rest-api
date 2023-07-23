package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.showseat.ShowSeatBulkCreateRequest;
import com.programmers.ticketing.dto.showseat.ShowSeatCreateRequest;
import com.programmers.ticketing.dto.showseat.ShowSeatDto;
import com.programmers.ticketing.service.ShowSeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowSeatApiController {
    private final ShowSeatService showSeatService;

    public ShowSeatApiController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @GetMapping("/show-seats")
    public ResponseEntity<Result<List<ShowSeatDto>>> findShowSeats(@RequestParam("showInformationId") Long showInformationId) {
        List<ShowSeatDto> showSeats = showSeatService.findShowSeats(showInformationId);
        return ResponseEntity.ok().body(new Result<>(showSeats));
    }

    @PostMapping("/show-seats")
    public ResponseEntity<Result<List<Long>>> registerShowSeats(@RequestBody @Valid ShowSeatBulkCreateRequest request) {
        List<Long> showSeatIds = showSeatService.registerShowSeats(
                request.getShowInformationId(),
                request.getSeatGradeId(),
                request.getSeatIds(),
                request.getFee()
        );
        URI location = URI.create("/api/v1/show-seats");
        return ResponseEntity.created(location).body(new Result<>(showSeatIds));
    }

    @GetMapping("/show-seats/{showSeatId}")
    public ResponseEntity<Result<ShowSeatDto>> findShowSeat(@PathVariable("showSeatId") Long showSeatId) {
        ShowSeatDto showSeat = showSeatService.findShowSeat(showSeatId);
        return ResponseEntity.ok().body(new Result<>(showSeat));
    }
}
