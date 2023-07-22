package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.show.ShowDto;
import com.programmers.ticketing.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Transactional
    public Long registerShow(String title, ShowType showType, LocalTime playTime, String description) {
        Show show = new Show(title, showType, playTime, description);
        showRepository.save(show);
        return show.getShowId();
    }

    @Transactional(readOnly = true)
    public ShowDto findShow(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("No such Show exist: ShowId {}", showId);
                    return new NoSuchElementException("No such Show exist");
                });
        return ShowDto.from(show);
    }

    @Transactional(readOnly = true)
    public Page<ShowDto> findShows(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Show> shows = showRepository.findAll(pageRequest);
        return shows.map(ShowDto::from);
    }

    @Transactional
    public void updateShow(Long showId, LocalTime playtime, String description) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("No such Show exist - ShowId: {}", showId);
                    return new NoSuchElementException("No such Show exist");
                });
        show.update(playtime, description);
    }

    @Transactional
    public void deleteShow(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("No such Show exist - ShowId: {}", showId);
                    return new NoSuchElementException("No such Show exist");
                });
        showRepository.delete(show);
    }
}
