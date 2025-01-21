package com.grinder.domain.tabling.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTablingEntity is a Querydsl query type for TablingEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTablingEntity extends EntityPathBase<TablingEntity> {

    private static final long serialVersionUID = -1565834698L;

    public static final QTablingEntity tablingEntity = new QTablingEntity("tablingEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> numberOfGuests = createNumber("numberOfGuests", Integer.class);

    public final TimePath<java.time.LocalTime> reserveTime = createTime("reserveTime", java.time.LocalTime.class);

    public final EnumPath<com.grinder.domain.tabling.model.TablingStatus> status = createEnum("status", com.grinder.domain.tabling.model.TablingStatus.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTablingEntity(String variable) {
        super(TablingEntity.class, forVariable(variable));
    }

    public QTablingEntity(Path<? extends TablingEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTablingEntity(PathMetadata metadata) {
        super(TablingEntity.class, metadata);
    }

}

