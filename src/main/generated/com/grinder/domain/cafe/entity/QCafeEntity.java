package com.grinder.domain.cafe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCafeEntity is a Querydsl query type for CafeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCafeEntity extends EntityPathBase<CafeEntity> {

    private static final long serialVersionUID = 609248298L;

    public static final QCafeEntity cafeEntity = new QCafeEntity("cafeEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final StringPath address = createString("address");

    public final StringPath businessNumber = createString("businessNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath tel = createString("tel");

    public QCafeEntity(String variable) {
        super(CafeEntity.class, forVariable(variable));
    }

    public QCafeEntity(Path<? extends CafeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCafeEntity(PathMetadata metadata) {
        super(CafeEntity.class, metadata);
    }

}

