package com.programmers.ticketing.controller;

import com.programmers.ticketing.dto.showinformation.ShowInformationCreateRequest;
import com.programmers.ticketing.dto.showinformation.ShowInformationUpdateRequest;
import com.programmers.ticketing.dto.showinformation.ShowInformationDto;
import com.programmers.ticketing.dto.showinformation.ShowInformationsFindRequest;
import com.programmers.ticketing.service.ShowInformationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowInformationApiController {
    private final ShowInformationService showInformationService;

    public ShowInformationApiController(ShowInformationService showInformationService) {
        this.showInformationService = showInformationService;
    }

    @GetMapping("/show-informations")
    public ResponseEntity<Result<List<ShowInformationDto>>> findShowInformations(@ModelAttribute @Valid ShowInformationsFindRequest request) {
        List<ShowInformationDto> showInformations = showInformationService.findShowInformations(
                request.getShowId(),
                request.getTheaterId(),
                request.getStartTime());
        return ResponseEntity.ok().body(new Result<>(showInformations));
    }

    @PostMapping("/show-informations")
    public ResponseEntity<Result<Long>> registerShowInformation(@RequestBody @Valid ShowInformationCreateRequest request) {
        Long showInformationId = showInformationService.registerShowInformation(
                request.getShowId(),
                request.getTheaterId(),
                request.getStartTime());
        URI location = URI.create("/api/v1/show-informations/" + showInformationId);
        return ResponseEntity.created(location).body(new Result<>(showInformationId));
    }

    @GetMapping("/show-informations/{showInformationId}")
    public ResponseEntity<Result<ShowInformationDto>> findShowInformation(@PathVariable("showInformationId") Long showInformationId) {
        ShowInformationDto showInformation = showInformationService.findShowInformation(showInformationId);
        return ResponseEntity.ok().body(new Result<>(showInformation));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/show-informations/{showInformationsId}")
    public void updateShowInformation(@PathVariable("showInformationsId") Long showInformationsId,
                                      @RequestBody @Valid ShowInformationUpdateRequest request) {
        showInformationService.updateShowInformation(
                showInformationsId,
                request.getShowStatus(),
                request.getStartTime());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/show-informations/{showInformationId}")
    public void deleteShowInformation(@PathVariable("showInformationId") Long showInformationId) {
        showInformationService.deleteShowInformation(showInformationId);
    }
}
