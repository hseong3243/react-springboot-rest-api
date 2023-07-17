package com.programmers.ticketing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeatPosition is a Querydsl query type for SeatPosition
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSeatPosition extends BeanPath<SeatPosition> {

    private static final long serialVersionUID = 1411367618L;

    public static final QSeatPosition seatPosition = new QSeatPosition("seatPosition");

    public final NumberPath<Integer> seatNumber = createNumber("seatNumber", Integer.class);

    public final NumberPath<Integer> seatRow = createNumber("seatRow", Integer.class);

    public final NumberPath<Integer> section = createNumber("section", Integer.class);

    public QSeatPosition(String variable) {
        super(SeatPosition.class, forVariable(variable));
    }

    public QSeatPosition(Path<? extends SeatPosition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeatPosition(PathMetadata metadata) {
        super(SeatPosition.class, metadata);
    }

}

