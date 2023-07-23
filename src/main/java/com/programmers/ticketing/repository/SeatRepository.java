package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.SeatPosition;
import com.programmers.ticketing.domain.Seat;
import com.programmers.ticketing.domain.ShowInformation;
import com.programmers.ticketing.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {
    Optional<Seat> findByTheaterAndSeatPosition(Theater theater, SeatPosition position);

    @Query("select s from Seat s join fetch s.theater t where s.seatId = :seatId")
    Optional<Seat> findSeatWithTheater(@Param("seatId") Long seatId);

    List<Seat> findAllBySeatIdIn(List<Long> seatIds);

    @Query("select s1 from Seat s1" +
            " where s1 not in (select s2 from ShowSeat ss" +
            " join ss.seat s2" +
            " where ss.showInformation = :showInformation" +
            " and ss.seat.seatId in :seatIds)" +
            " and s1 in :seatIds")
    List<Seat> findSeatsNotExistShowSeatByShowInformationAndSeatIdsIn(@Param("showInformation") ShowInformation showInformation,
                                                                      @Param("seatIds") List<Long> seatIds);

    @Query("select s from Seat s where s.theater = :theater and s.seatPosition.section = :section")
    List<Seat> findAllByTheaterAndSection(@Param("theater") Theater theater,
                                          @Param("section") int section);
}
