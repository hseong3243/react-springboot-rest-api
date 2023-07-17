package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.ShowInformation;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowInformationRepositoryCustom {
    List<ShowInformation> findShowInformations(Long showId, Long theaterId, LocalDateTime startTime);
}
