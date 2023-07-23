package com.programmers.ticketing.scheduler;

import com.programmers.ticketing.domain.ShowStatus;
import com.programmers.ticketing.repository.ShowInformationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
public class ShowStatusScheduler {
    private final ShowInformationRepository showInformationRepository;

    public ShowStatusScheduler(ShowInformationRepository showInformationRepository) {
        this.showInformationRepository = showInformationRepository;
    }

    @Scheduled(cron = "* * * * *")
    public void updateShowInformationStartTimeBeforeNow() {
        showInformationRepository.updateShowStatusBeforeNow(
                ShowStatus.BEFORE,
                ShowStatus.STAGING,
                LocalDateTime.now());
    }

    @Scheduled(cron = "* * * * *")
    public void updateShowInformationEndTimeBeforeNow() {
        showInformationRepository.updateShowStatusBeforeEndTime(
                ShowStatus.STAGING.name(),
                ShowStatus.END.name(),
                LocalDateTime.now());
    }
}
