package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.theater.TheaterDto;
import com.programmers.ticketing.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Transactional
    public Long registerTheater(String name, String address) {
        theaterRepository.findByName(name)
                .ifPresent(theater -> {
                    throw new DuplicateKeyException("Theater name duplicate");
                });
        Theater theater = new Theater(name, address);
        theaterRepository.save(theater);
        return theater.getTheaterId();
    }

    @Transactional(readOnly = true)
    public TheaterDto findTheater(Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such Theater exist");
                });
        return TheaterDto.from(theater);
    }

    @Transactional(readOnly = true)
    public List<TheaterDto> findTheaters() {
        List<Theater> theaters = theaterRepository.findAll();
        return theaters.stream()
                .map(TheaterDto::from)
                .toList();
    }

    @Transactional
    public void updateTheater(Long theaterId, String name, String address) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such Theater exist");
                });
        theater.update(name, address);
    }

    @Transactional
    public void deleteTheater(Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> {
                    log.warn("No such theater exist - TheaterId: {}", theaterId);
                    return new NoSuchElementException("No such Theater exist");
                });
        theaterRepository.delete(theater);
    }
}
