package com.grinder.domain.tabling.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBlockedDateEntity is a Querydsl query type for BlockedDateEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBlockedDateEntity extends EntityPathBase<BlockedDateEntity> {

    private static final long serialVersionUID = -670883419L;

    public static final QBlockedDateEntity blockedDateEntity = new QBlockedDateEntity("blockedDateEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath reason = createString("reason");

    public QBlockedDateEntity(String variable) {
        super(BlockedDateEntity.class, forVariable(variable));
    }

    public QBlockedDateEntity(Path<? extends BlockedDateEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlockedDateEntity(PathMetadata metadata) {
        super(BlockedDateEntity.class, metadata);
    }

}

