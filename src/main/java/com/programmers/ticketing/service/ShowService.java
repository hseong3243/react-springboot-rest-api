package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
}
