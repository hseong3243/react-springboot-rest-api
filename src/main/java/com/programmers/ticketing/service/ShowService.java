package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.dto.show.ShowDto;
import com.programmers.ticketing.exception.FileUploadException;
import com.programmers.ticketing.repository.ImageRepository;
import com.programmers.ticketing.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final ImageRepository imageRepository;

    public ShowService(ShowRepository showRepository, ImageRepository imageRepository) {
        this.showRepository = showRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Long registerShow(String title,
                             ShowType showType,
                             LocalTime playTime,
                             String description,
                             MultipartFile imageFile) {
        String imageName = null;
        if(imageFile != null) {
            imageName = imageUpload(imageFile);
        }
        Show show = new Show(title, showType, playTime, description, imageName);
        showRepository.save(show);
        return show.getShowId();
    }

    private String imageUpload(MultipartFile imageFile) {
        String imageName;
        try {
            imageName = imageRepository.uploadImage(imageFile);
        } catch (IOException e) {
            throw new FileUploadException("File upload fail");
        }
        return imageName;
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
    public Page<ShowDto> findShows(String title, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Show> shows = showRepository.findAllByTitleContaining(title, pageRequest);
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

    public void addImage(Long showId, MultipartFile image) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NoSuchElementException("No such Show exist"));
        String imageName = null;
        try {
            imageName = imageRepository.uploadImage(image);
        } catch (IOException e) {
            throw new RuntimeException("Image upload fail");
        }
        show.setImagePath(imageName);
    }
}
