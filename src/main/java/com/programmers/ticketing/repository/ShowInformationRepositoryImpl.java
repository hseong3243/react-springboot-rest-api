package com.programmers.ticketing.repository;

import com.programmers.ticketing.domain.QShow;
import com.programmers.ticketing.domain.QTheater;
import com.programmers.ticketing.domain.ShowInformation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.programmers.ticketing.domain.QShow.show;
import static com.programmers.ticketing.domain.QShowInformation.showInformation;
import static com.programmers.ticketing.domain.QTheater.theater;

@Repository
public class ShowInformationRepositoryImpl implements ShowInformationRepositoryCustom {
    private final JPAQueryFactory query;

    public ShowInformationRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ShowInformation> findShowInformations(Long showId, Long theaterId, LocalDateTime startTime) {
        return query.selectFrom(showInformation)
                .join(showInformation.show, show).fetchJoin()
                .join(showInformation.theater, theater).fetchJoin()
                .where(showIdEq(showId),
                        theaterIdEq(theaterId),
                        startTimeGoe(startTime))
                .fetch();
    }

    private BooleanExpression showIdEq(Long showId) {
        return showId != null ? showInformation.show.showId.eq(showId) : null;
    }

    private BooleanExpression theaterIdEq(Long theaterId) {
        return theaterId != null ? showInformation.theater.theaterId.eq(theaterId): null;
    }

    private BooleanExpression startTimeGoe(LocalDateTime startTime) {
        return startTime != null ? showInformation.startTime.goe(startTime): null;
    }
}
