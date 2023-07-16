package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.repository.ShowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShowServiceTest {

    @InjectMocks
    private ShowService showService;

    @Mock
    private ShowRepository showRepository;

    @Test
    @DisplayName("성공: show 등록 기능")
    void registerShow() {
        //given
        String title = "title";
        ShowType showType = ShowType.valueOf("MUSICAL");
        LocalTime playtime = LocalTime.of(2, 30, 0);

        //when
        showService.registerShow(title, showType, playtime);

        //then
        then(showRepository).should().save(any());
    }
}