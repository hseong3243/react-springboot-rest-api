package com.programmers.ticketing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShowSeat is a Querydsl query type for ShowSeat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowSeat extends EntityPathBase<ShowSeat> {

    private static final long serialVersionUID = 1111653654L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShowSeat showSeat = new QShowSeat("showSeat");

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final QSeat seat;

    public final QSeatGrade seatGrade;

    public final QShowInformation showInformation;

    public final NumberPath<Long> showSeatId = createNumber("showSeatId", Long.class);

    public QShowSeat(String variable) {
        this(ShowSeat.class, forVariable(variable), INITS);
    }

    public QShowSeat(Path<? extends ShowSeat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShowSeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShowSeat(PathMetadata metadata, PathInits inits) {
        this(ShowSeat.class, metadata, inits);
    }

    public QShowSeat(Class<? extends ShowSeat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.seat = inits.isInitialized("seat") ? new QSeat(forProperty("seat"), inits.get("seat")) : null;
        this.seatGrade = inits.isInitialized("seatGrade") ? new QSeatGrade(forProperty("seatGrade")) : null;
        this.showInformation = inits.isInitialized("showInformation") ? new QShowInformation(forProperty("showInformation"), inits.get("showInformation")) : null;
    }

}

