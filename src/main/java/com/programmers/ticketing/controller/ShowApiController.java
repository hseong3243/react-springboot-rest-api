package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.show.ShowCreateRequest;
import com.programmers.ticketing.dto.show.ShowDto;
import com.programmers.ticketing.dto.show.ShowUpdateRequest;
import com.programmers.ticketing.dto.show.ShowsFindRequest;
import com.programmers.ticketing.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowApiController {
    private final ShowService showService;

    public ShowApiController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/shows")
    public ResponseEntity<PageResult<ShowDto>> findShows(@ModelAttribute @Valid ShowsFindRequest request) {
        Page<ShowDto> shows = showService.findShows(request.getPage(), request.getSize());
        return ResponseEntity.ok().body(new PageResult<>(shows));
    }

    @PostMapping("/shows")
    public ResponseEntity<Result<Long>> registerShow(@RequestBody @Valid ShowCreateRequest request) {
        Long showId = showService.registerShow(
                request.getTitle(),
                request.getShowType(),
                request.getPlaytime(),
                request.getDescription());
        URI location = URI.create("/api/v1/shows/" + showId);
        return ResponseEntity.created(location).body(new Result<>(showId));
    }

    @GetMapping("/shows/{showId}")
    public ResponseEntity<Result<ShowDto>> findShow(@PathVariable("showId") Long showId) {
        ShowDto show = showService.findShow(showId);
        return ResponseEntity.ok().body(new Result<>(show));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/shows/{showId}")
    public void updateShow(@PathVariable("showId") Long showId, @RequestBody @Valid ShowUpdateRequest request) {
        showService.updateShow(
                showId,
                request.getPlaytime(),
                request.getDescription());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/shows/{showId}")
    public void deleteShow(@PathVariable("showId") Long showId) {
        showService.deleteShow(showId);
    }
}
