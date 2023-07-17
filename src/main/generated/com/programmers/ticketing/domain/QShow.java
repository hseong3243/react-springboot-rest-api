package com.programmers.ticketing.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShow is a Querydsl query type for Show
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShow extends EntityPathBase<Show> {

    private static final long serialVersionUID = 1032346481L;

    public static final QShow show = new QShow("show");

    public final StringPath description = createString("description");

    public final TimePath<java.time.LocalTime> playtime = createTime("playtime", java.time.LocalTime.class);

    public final NumberPath<Long> showId = createNumber("showId", Long.class);

    public final EnumPath<ShowType> showType = createEnum("showType", ShowType.class);

    public final StringPath title = createString("title");

    public QShow(String variable) {
        super(Show.class, forVariable(variable));
    }

    public QShow(Path<? extends Show> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShow(PathMetadata metadata) {
        super(Show.class, metadata);
    }

}

