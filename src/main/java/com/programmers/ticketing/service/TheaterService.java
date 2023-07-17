package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.repository.TheaterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Transactional
    public Long registerTheater(String name, String address) {
        Theater theater = new Theater(name, address);
        theaterRepository.save(theater);
        return theater.getTheaterId();
    }
}
