package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShowInformationMySQLRepositoryTest {
    @Autowired
    private ShowInformationRepository showInformationRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("성공: showInformation 상태 업데이트 - 시작시간과 공연시간이 현재 시간이 이전인 showInformation")
    void updateShowStatusTest() {
        //given
        ShowInformation target = createAndSaveForShowInformation("titleA", LocalDateTime.now());
        ShowInformation noneTarget = createAndSaveForShowInformation("titleB", LocalDateTime.now().plusHours(1));

        //when
        showInformationRepository.updateShowStatusBeforeEndTime(
                ShowStatus.BEFORE.name(),
                ShowStatus.END.name(),
                LocalDateTime.now().plusHours(1));
        em.clear();

        //then
        List<ShowInformation> result = showInformationRepository.findAll();
        List<ShowStatus> showStatuses = result.stream().map(ShowInformation::getShowStatus).toList();
        assertThat(showStatuses).containsExactlyInAnyOrder(ShowStatus.BEFORE, ShowStatus.END);
    }

    private ShowInformation createAndSaveForShowInformation(String showTitle, LocalDateTime startTime) {
        Show show = new Show(
                showTitle,
                ShowType.MUSICAL,
                LocalTime.MIN,
                "설명",
                null);
        Theater theater = new Theater("name", "address");
        ShowInformation showInformation = new ShowInformation(
                show,
                theater,
                startTime.plusSeconds(1));
        showRepository.save(show);
        theaterRepository.save(theater);
        showInformationRepository.save(showInformation);
        return showInformation;
    }
}
