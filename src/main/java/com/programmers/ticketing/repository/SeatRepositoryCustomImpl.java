package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.QSeat;
import com.programmers.ticketing.domain.QTheater;
import com.programmers.ticketing.domain.Seat;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.programmers.ticketing.domain.QSeat.seat;
import static com.programmers.ticketing.domain.QTheater.theater;

@Repository
public class SeatRepositoryCustomImpl implements SeatRepositoryCustom {
    private final JPAQueryFactory query;

    public SeatRepositoryCustomImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public List<Seat> findSeats(Long theaterId) {
        return query.selectFrom(seat)
                .join(seat.theater, theater).fetchJoin()
                .where(theaterIdEq(theaterId))
                .fetch();
    }

    private BooleanExpression theaterIdEq(Long theaterId) {
        return theaterId != null ? theater.theaterId.eq(theaterId) : null;
    }
}
