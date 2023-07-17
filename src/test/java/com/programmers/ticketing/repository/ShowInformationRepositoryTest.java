package com.programmers.ticketing.repository;

import com.programmers.ticketing.TicketingTestUtil;
import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.domain.Theater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.programmers.ticketing.TicketingTestUtil.createShow;
import static com.programmers.ticketing.TicketingTestUtil.createTheater;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShowInformationRepositoryTest {
    @Autowired
    private ShowInformationRepository showInformationRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @BeforeEach
    void init() {
        List<Show> shows = TicketingTestUtil.createShows(5);
        List<Theater> theaters = TicketingTestUtil.createTheaters(5);
        showRepository.saveAll(shows);
        theaterRepository.saveAll(theaters);
    }

    @Test
    @DisplayName("성공: showInformation 단건 조회 - show, theater 조인")
    void findShowInformationWithShowAndTheater() {
        //given
        Show show = new Show("title", ShowType.CONCERT, LocalTime.of(2, 30), "");
        Theater theater = new Theater("theater", "address");
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        ShowInformation showInformation = new ShowInformation(show, theater, startTime);
        showRepository.save(show);
        theaterRepository.save(theater);
        showInformationRepository.save(showInformation);

        //when
        Optional<ShowInformation> optionalShowInformation = showInformationRepository
                .findShowInformationWithShowAndTheater(showInformation.getShowInformationId());

        //then
        assertThat(optionalShowInformation).isNotNull();
        ShowInformation findShowInformation = optionalShowInformation.get();
        assertThat(findShowInformation.getShow()).isEqualTo(show);
        assertThat(findShowInformation.getTheater()).isEqualTo(theater);
    }

    @Test
    @DisplayName("성공: showInformation 목록 조회")
    void findShowInformations() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        Theater theater = createTheater("nameA");
        theaterRepository.save(theater);
        Show show = createShow("titleA");
        showRepository.save(show);

        ShowInformation showInformationA = new ShowInformation(show, theater, startTime);
        ShowInformation showInformationB = new ShowInformation(show, theater, startTime);
        ShowInformation showInformationC = new ShowInformation(show, theater, startTime);
        showInformationRepository.save(showInformationA);
        showInformationRepository.save(showInformationB);
        showInformationRepository.save(showInformationC);

        //when
        List<ShowInformation> showInformations =
                showInformationRepository.findShowInformations(null, null, null);

        //then
        assertThat(showInformations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("성공: showInformation 목록 조회 - showId")
    void findShowInformations_ByShowId() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        Theater theater = createTheater("nameA");
        theaterRepository.save(theater);

        Show showA = createShow("titleA");
        showRepository.save(showA);
        ShowInformation showInformationA = new ShowInformation(showA, theater, startTime);
        ShowInformation showInformationB = new ShowInformation(showA, theater, startTime);
        showInformationRepository.save(showInformationA);
        showInformationRepository.save(showInformationB);

        Show showB = createShow("titleB");
        showRepository.save(showB);
        ShowInformation showInformationC = new ShowInformation(showB, theater, startTime);
        showInformationRepository.save(showInformationC);

        //when
        List<ShowInformation> showInformations =
                showInformationRepository.findShowInformations(showA.getShowId(), null, null);

        //then
        assertThat(showInformations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("성공: showInformation 목록 조회 - theaterId")
    void findShowInformations_ByTheaterId() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(1);
        Show show = createShow("titleA");
        showRepository.save(show);

        Theater theaterA = createTheater("nameA");
        theaterRepository.save(theaterA);
        ShowInformation showInformationA = new ShowInformation(show, theaterA, startTime);
        ShowInformation showInformationB = new ShowInformation(show, theaterA, startTime);
        showInformationRepository.save(showInformationA);
        showInformationRepository.save(showInformationB);

        Theater theaterB = createTheater("nameB");
        theaterRepository.save(theaterB);
        ShowInformation showInformationC = new ShowInformation(show, theaterB, startTime);
        showInformationRepository.save(showInformationC);

        //when
        List<ShowInformation> showInformations =
                showInformationRepository.findShowInformations(null, theaterA.getTheaterId(), null);

        //then
        assertThat(showInformations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("성공: showInformation 목록 조회 - startTime")
    void findShowInformations_ByStartTime() {
        //given
        Show show = createShow("titleA");
        showRepository.save(show);
        Theater theater = createTheater("nameA");
        theaterRepository.save(theater);

        LocalDateTime startTimeA = LocalDateTime.now().plusHours(2);
        ShowInformation showInformationA = new ShowInformation(show, theater, startTimeA);
        ShowInformation showInformationB = new ShowInformation(show, theater, startTimeA);
        showInformationRepository.save(showInformationA);
        showInformationRepository.save(showInformationB);

        LocalDateTime startTimeB = LocalDateTime.now().plusHours(1);
        ShowInformation showInformationC = new ShowInformation(show, theater, startTimeB);
        showInformationRepository.save(showInformationC);

        //when
        List<ShowInformation> showInformations =
                showInformationRepository.findShowInformations(null, null, startTimeA.minusMinutes(1));

        //then
        assertThat(showInformations.size()).isEqualTo(2);
    }
}