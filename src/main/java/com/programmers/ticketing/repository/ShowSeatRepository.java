package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Query("select ss from ShowSeat ss" +
            " join fetch ss.seat s" +
            " join fetch ss.seatGrade sg" +
            " where ss.showInformation = :showInformation")
    List<ShowSeat> findAllByShowInformationWithSeatAndSeatGrade(@Param("showInformation") ShowInformation showInformation);

    @Query("select ss from ShowSeat ss" +
            " join fetch ss.seat s" +
            " join fetch ss.seatGrade sg" +
            " where ss.showSeatId = :showSeatId")
    Optional<ShowSeat> findByIdWithSeatAndSeatGrade(@Param("showSeatId") Long showSeatId);

    Optional<ShowSeat> findByShowInformationAndSeat(ShowInformation showInformation, Seat seat);
}
