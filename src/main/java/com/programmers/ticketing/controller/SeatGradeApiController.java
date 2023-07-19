package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.SeatGradeDto;
import com.programmers.ticketing.dto.seatgrade.SeatGradeCreateRequest;
import com.programmers.ticketing.service.SeatGradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SeatGradeApiController {
    private final SeatGradeService seatGradeService;

    public SeatGradeApiController(SeatGradeService seatGradeService) {
        this.seatGradeService = seatGradeService;
    }

    @GetMapping("/seat-grades")
    public ResponseEntity<Result<List<SeatGradeDto>>> findSeatGrades() {
        List<SeatGradeDto> seatGrades = seatGradeService.findSeatGrades();
        return ResponseEntity.ok().body(new Result<>(seatGrades));
    }

    @PostMapping("/seat-grades")
    public ResponseEntity<Result<Long>> registerSeatGrade(@RequestBody @Valid SeatGradeCreateRequest request) {
        Long seatGradeId = seatGradeService.registerSeatGrade(request.getName());
        URI location = URI.create("/seat-grades");
        return ResponseEntity.created(location).body(new Result<>(seatGradeId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/seat-grades/{seatGradeId}")
    public void deleteSeatGrade(@PathVariable("seatGradeId") Long seatGradeId) {
        seatGradeService.deleteSeatGrade(seatGradeId);
    }
}
