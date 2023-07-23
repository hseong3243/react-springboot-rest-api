package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ShowInformationRepository extends JpaRepository<ShowInformation, Long>, ShowInformationRepositoryCustom {
    @Query("select si from ShowInformation si" +
            " join fetch si.show s" +
            " join fetch si.theater t" +
            " where si.showInformationId = :showInformationId")
    Optional<ShowInformation> findShowInformationWithShowAndTheater(@Param("showInformationId") Long showInformationId);

    @Modifying
    @Query("update ShowInformation si set si.showStatus = :after" +
            " where si.showStatus = :before" +
            " and si.startTime <= :now")
    void updateShowStatusBeforeNow(@Param("before") ShowStatus before,
                                   @Param("after") ShowStatus after,
                                   @Param("now") LocalDateTime now);

    @Modifying
    @Query(nativeQuery = true,
            value = "update show_information si" +
                    " set si.show_status = :after" +
                    " where si.show_id in (" +
                    "select s.show_id" +
                    " from shows s" +
                    " where si.show_status = :before" +
                    " and ADDTIME(si.start_time, s.playtime) <= :now" +
                    ")")
    void updateShowStatusBeforeEndTime(@Param("before") String beforeShowStatus,
                              @Param("after") String afterShowStatus,
                              @Param("now") LocalDateTime now);
}
