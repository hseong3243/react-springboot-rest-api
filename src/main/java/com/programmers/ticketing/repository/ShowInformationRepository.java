package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Show;
import com.programmers.ticketing.domain.ShowInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShowInformationRepository extends JpaRepository<ShowInformation, Long>, ShowInformationRepositoryCustom {
    @Query("select si from ShowInformation si" +
            " join fetch si.show s" +
            " join fetch si.theater t" +
            " where si.showInformationId = :showInformationId")
    Optional<ShowInformation> findShowInformationWithShowAndTheater(@Param("showInformationId") Long showInformationId);
}
