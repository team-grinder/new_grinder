package com.grinder.domain.cafe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCafeBusinessHourEntity is a Querydsl query type for CafeBusinessHourEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCafeBusinessHourEntity extends EntityPathBase<CafeBusinessHourEntity> {

    private static final long serialVersionUID = 1855456398L;

    public static final QCafeBusinessHourEntity cafeBusinessHourEntity = new QCafeBusinessHourEntity("cafeBusinessHourEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final ListPath<java.time.LocalTime, TimePath<java.time.LocalTime>> blockedTimes = this.<java.time.LocalTime, TimePath<java.time.LocalTime>>createList("blockedTimes", java.time.LocalTime.class, TimePath.class, PathInits.DIRECT2);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxTimePerReservation = createNumber("maxTimePerReservation", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public QCafeBusinessHourEntity(String variable) {
        super(CafeBusinessHourEntity.class, forVariable(variable));
    }

    public QCafeBusinessHourEntity(Path<? extends CafeBusinessHourEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCafeBusinessHourEntity(PathMetadata metadata) {
        super(CafeBusinessHourEntity.class, metadata);
    }

}

