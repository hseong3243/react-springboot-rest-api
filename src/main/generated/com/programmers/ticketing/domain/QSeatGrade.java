package com.programmers.ticketing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeatGrade is a Querydsl query type for SeatGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeatGrade extends EntityPathBase<SeatGrade> {

    private static final long serialVersionUID = -468671266L;

    public static final QSeatGrade seatGrade = new QSeatGrade("seatGrade");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> seatGradeId = createNumber("seatGradeId", Long.class);

    public QSeatGrade(String variable) {
        super(SeatGrade.class, forVariable(variable));
    }

    public QSeatGrade(Path<? extends SeatGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeatGrade(PathMetadata metadata) {
        super(SeatGrade.class, metadata);
    }

}

