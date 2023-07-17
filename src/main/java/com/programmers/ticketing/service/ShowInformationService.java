package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowRepository;
import com.programmers.ticketing.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ShowInformationService {
    private final ShowInformationRepository showInformationRepository;
    private final ShowRepository showRepository;
    private final TheaterRepository theaterRepository;

    public ShowInformationService(ShowInformationRepository showInformationRepository,
                                  ShowRepository showRepository,
                                  TheaterRepository theaterRepository) {
        this.showInformationRepository = showInformationRepository;
        this.showRepository = showRepository;
        this.theaterRepository = theaterRepository;
    }

    @Transactional
    public Long registerShowInformation(Long showId, Long theaterId, LocalDateTime startTime) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("No such show exist - ShowId: {}", showId);
                    return new NoSuchElementException("No such show exist");
                });
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such theater exist");
                });

        ShowInformation showInformation = new ShowInformation(show, theater, startTime);
        showInformationRepository.save(showInformation);
        return showInformation.getShowInformationId();
    }

    @Transactional(readOnly = true)
    public ShowInformationDto findShowInformation(Long showInformationId) {
        ShowInformation showInformation = showInformationRepository
                .findShowInformationWithShowAndTheater(showInformationId)
                .orElseThrow(() -> {
                    log.warn("No such show information - ShowInformationId: {}", showInformationId);
                    return new NoSuchElementException("No such show information");
                });
        return ShowInformationDto.from(showInformation, showInformation.getShow(), showInformation.getTheater());
    }
}
