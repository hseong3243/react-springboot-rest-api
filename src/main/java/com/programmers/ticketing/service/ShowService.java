package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.ShowDto;
import com.programmers.ticketing.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Long registerShow(String title, ShowType showType, LocalTime playTime) {
        Show show = new Show(title, showType, playTime);
        showRepository.save(show);
        return show.getShowId();
    }

    public ShowDto findShow(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> {
                    log.warn("No such Show exist: ShowId {}", showId);
                    return new NoSuchElementException("No such Show exist");
                });
        return ShowDto.from(show);
    }
}
