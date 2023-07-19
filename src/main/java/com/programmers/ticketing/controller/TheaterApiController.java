package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.TheaterDto;
import com.programmers.ticketing.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TheaterApiController {
    private final TheaterService theaterService;

    public TheaterApiController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/theaters")
    public ResponseEntity<Result<List<TheaterDto>>> findTheaters() {
        List<TheaterDto> theaters = theaterService.findTheaters();
        return ResponseEntity.ok().body(new Result<>(theaters));
    }

    @PostMapping("/theaters")
    public ResponseEntity<Result<Long>> registerTheater(@RequestBody @Valid TheaterCreateRequest request) {
        Long theaterId = theaterService.registerTheater(
                request.getName(),
                request.getAddress());
        URI location = URI.create("/api/v1/theaters/" + theaterId);
        return ResponseEntity.created(location).body(new Result<>(theaterId));
    }

    @GetMapping("/theaters/{theaterId}")
    public ResponseEntity<Result<TheaterDto>> findTheater(@PathVariable("theaterId") Long theaterId) {
        TheaterDto theater = theaterService.findTheater(theaterId);
        return ResponseEntity.ok().body(new Result<>(theater));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/theaters/{theaterId}")
    public void updateTheater(@PathVariable("theaterId") Long theaterId,
                              @RequestBody @Valid TheaterUpdateRequest request) {
        theaterService.updateTheater(theaterId, request.getName(), request.getAddress());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/theaters/{theaterId}")
    public void deleteTheater(@PathVariable("theaterId") Long theaterId) {
        theaterService.deleteTheater(theaterId);
    }
}
