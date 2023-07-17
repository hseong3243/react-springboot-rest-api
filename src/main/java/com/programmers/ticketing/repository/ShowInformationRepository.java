package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.ShowInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowInformationRepository extends JpaRepository<ShowInformation, Long> {
}
