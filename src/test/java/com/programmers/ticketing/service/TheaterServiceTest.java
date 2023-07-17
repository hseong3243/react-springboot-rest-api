package com.programmers.ticketing.service;

import com.programmers.ticketing.repository.TheaterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TheaterServiceTest {

    @InjectMocks
    private TheaterService theaterService;

    @Mock
    private TheaterRepository theaterRepository;

    @Test
    @DisplayName("성공: theater 단건 등록 기능")
    void registerTheater() {
        //given
        String name = "theater";
        String address = "seoul";

        //when
        theaterService.registerTheater(name, address);

        //then
        then(theaterRepository).should().save(any());
    }

    @Test
    @DisplayName("예외: theater 단건 등록 기능 - 범위를 넘은 name")
    void registerTheater_ButNameOutOfRange_Then_Exception() {
        //given
        String address = "seoul";

        //when
        String name = "a".repeat(101);

        //then
        assertThatThrownBy(() -> theaterService.registerTheater(name, address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: theater 단건 등록 기능 - 범위를 넘은 address")
    void registerTheater_ButAddressOutOfRange_Then_Exception() {
        //given
        String name = "name";

        //when
        String address = "a".repeat(201);

        //then
        assertThatThrownBy(() -> theaterService.registerTheater(name, address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: theater 단건 등록 기능 - null 값의 name")
    void registerTheater_ButNameIsNull_Then_Exception() {
        //given
        String address = "address";

        //when
        String name = null;

        //then
        assertThatThrownBy(() -> theaterService.registerTheater(name, address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: theater 단건 등록 기능 - null 값의 address")
    void registerTheater_ButAddressIsNull_Then_Exception() {
        //given
        String name = "name";

        //when
        String address = null;

        //then
        assertThatThrownBy(() -> theaterService.registerTheater(name, address))
                .isInstanceOf(IllegalArgumentException.class);
    }


}