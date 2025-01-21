package com.grinder.domain.tabling.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTablingTimeSlotEntity is a Querydsl query type for TablingTimeSlotEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTablingTimeSlotEntity extends EntityPathBase<TablingTimeSlotEntity> {

    private static final long serialVersionUID = 918614401L;

    public static final QTablingTimeSlotEntity tablingTimeSlotEntity = new QTablingTimeSlotEntity("tablingTimeSlotEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> currentGuests = createNumber("currentGuests", Integer.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    public final NumberPath<Integer> maxGuests = createNumber("maxGuests", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final TimePath<java.time.LocalTime> reserveTime = createTime("reserveTime", java.time.LocalTime.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTablingTimeSlotEntity(String variable) {
        super(TablingTimeSlotEntity.class, forVariable(variable));
    }

    public QTablingTimeSlotEntity(Path<? extends TablingTimeSlotEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTablingTimeSlotEntity(PathMetadata metadata) {
        super(TablingTimeSlotEntity.class, metadata);
    }

}

