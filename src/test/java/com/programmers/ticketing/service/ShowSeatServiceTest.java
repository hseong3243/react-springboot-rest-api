package com.programmers.ticketing.service;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.*;
import com.programmers.ticketing.dto.ShowSeatDto;
import com.programmers.ticketing.dto.ShowSeatSeatDto;
import com.programmers.ticketing.repository.SeatGradeRepository;
import com.programmers.ticketing.repository.SeatRepository;
import com.programmers.ticketing.repository.ShowInformationRepository;
import com.programmers.ticketing.repository.ShowSeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.programmers.ticketing.TicketingTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ShowSeatServiceTest {
    @InjectMocks
    private ShowSeatService showSeatService;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private ShowInformationRepository showInformationRepository;

    @Mock
    private SeatGradeRepository seatGradeRepository;

    @Mock
    private SeatRepository seatRepository;

    @Test
    @DisplayName("성공: showSeat 단건 등록")
    void registerShowSeat() {
        //given
        ShowSeat showSeat = TicketingTestUtil.createShowSeat("vip", 100);
        ShowInformation showInformation = showSeat.getShowInformation();
        SeatGrade seatGrade = showSeat.getSeatGrade();
        Seat seat = showSeat.getSeat();

        given(showInformationRepository.findById(any())).willReturn(Optional.ofNullable(showInformation));
        given(seatGradeRepository.findById(any())).willReturn(Optional.ofNullable(seatGrade));
        given(seatRepository.findById(any())).willReturn(Optional.ofNullable(seat));

        //when
        showSeatService.registerShowSeat(1L, 1L, 1L, 100);

        //then
        then(showSeatRepository).should().save(any());
    }

    @Test
    @DisplayName("성공: showSeat 다건 등록 - 다수의 seatId")
    void registerMultipleShowSeat() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        Theater theater = createTheater("theater");
        Show show = createShow("title");
        ShowInformation showInformation = new ShowInformation(show, theater, startTime);
        SeatGrade seatGrade = new SeatGrade("vip");
        List<Seat> seats = createSeats(theater, 50);

        given(showInformationRepository.findById(any())).willReturn(Optional.of(showInformation));
        given(seatGradeRepository.findById(any())).willReturn(Optional.of(seatGrade));
        given(seatRepository.findAllBySeatIdIn(any())).willReturn(seats);


        //when
        showSeatService.registerMultipleShowSeat(1L, 1L, List.of(1L), 100);

        //then
        then(showSeatRepository).should().saveAll(any());
    }

    @Test
    @DisplayName("성공: showSeats 목록 조회")
    void findShowSeats() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        List<ShowSeat> showSeats = createShowSeats(50);
        ShowInformation showInformation = showSeats.get(0).getShowInformation();

        given(showInformationRepository.findById(any())).willReturn(Optional.of(showInformation));
        given(showSeatRepository.findAllByShowInformationWithShowInformationAndSeatAndSeatGrade(any()))
                .willReturn(showSeats);

        //when
        List<ShowSeatDto> result = showSeatService.findShowSeats(1L);

        //then
        List<ShowSeatDto> showSeatDtos = showSeats.stream().map(ShowSeatDto::from).toList();
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(showSeatDtos);
    }
}