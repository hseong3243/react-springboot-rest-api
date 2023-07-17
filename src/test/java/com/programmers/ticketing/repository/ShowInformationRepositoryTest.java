package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowType;
import com.programmers.ticketing.domain.Theater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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
}