package com.programmers.ticketing.service;

import com.programmers.ticketing.domain.Theater;
import com.programmers.ticketing.dto.TheaterDto;
import com.programmers.ticketing.repository.TheaterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @Test
    @DisplayName("성공: theater 단건 조회 기능")
    void findTheater() {
        //given
        Theater theater = new Theater("theater", "address");

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        TheaterDto theaterDto = theaterService.findTheater(1L);

        //then
        assertThat(theaterDto.getName()).isEqualTo(theater.getName());
        assertThat(theaterDto.getAddress()).isEqualTo(theater.getAddress());
    }

    @Test
    @DisplayName("성공: theater 목록 조회 기능")
    void findTheaters() {
        //given
        Theater theaterA = new Theater("theaterA", "address");
        Theater theaterB = new Theater("theaterB", "address");
        List<Theater> theaters = List.of(theaterA, theaterB);

        given(theaterRepository.findAll()).willReturn(theaters);

        //when
        List<TheaterDto> theaterDtos = theaterService.findTheaters();

        //then
        TheaterDto theaterDtoA = TheaterDto.from(theaterA);
        TheaterDto theaterDtoB = TheaterDto.from(theaterB);
        assertThat(theaterDtos).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(theaterDtoA, theaterDtoB);
    }

    @Test
    @DisplayName("성공: theater 업데이트 기능")
    void updateTheater() {
        //given
        Theater theater = new Theater("theater", "address");

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        String updatedName = "updateTheater";
        String updatedAddress = "updateAddress";
        theaterService.updateTheater(1L, updatedName, updatedAddress);

        //then
        assertThat(theater.getName()).isEqualTo("updateTheater");
        assertThat(theater.getAddress()).isEqualTo("updateAddress");
    }

    @Test
    @DisplayName("예외: theater 업데이트 기능 - 범위를 넘은 name")
    void updateTheater_ButNameOutOfRange_Then_Exception() {
        //given
        Theater theater = new Theater("theater", "address");

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        String updateName = "a".repeat(101);

        //then
        assertThatThrownBy(() -> theaterService.updateTheater(1L, updateName, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예외: theater 업데이트 기능 - 범위를 넘은 address")
    void updateTheater_ButAddressOutOfRange_Then_Exception() {
        //given
        Theater theater = new Theater("theater", "address");

        given(theaterRepository.findById(any())).willReturn(Optional.of(theater));

        //when
        String updatedAddress = "a".repeat(201);

        //then
        assertThatThrownBy(() -> theaterService.updateTheater(1L, null, updatedAddress))
                .isInstanceOf(IllegalArgumentException.class);
    }
}