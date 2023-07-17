package com.programmers.ticketing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShowInformation is a Querydsl query type for ShowInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowInformation extends EntityPathBase<ShowInformation> {

    private static final long serialVersionUID = -513089285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShowInformation showInformation = new QShowInformation("showInformation");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QShow show;

    public final NumberPath<Long> showInformationId = createNumber("showInformationId", Long.class);

    public final EnumPath<ShowStatus> showStatus = createEnum("showStatus", ShowStatus.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final QTheater theater;

    public QShowInformation(String variable) {
        this(ShowInformation.class, forVariable(variable), INITS);
    }

    public QShowInformation(Path<? extends ShowInformation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShowInformation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShowInformation(PathMetadata metadata, PathInits inits) {
        this(ShowInformation.class, metadata, inits);
    }

    public QShowInformation(Class<? extends ShowInformation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.show = inits.isInitialized("show") ? new QShow(forProperty("show")) : null;
        this.theater = inits.isInitialized("theater") ? new QTheater(forProperty("theater")) : null;
    }

}

