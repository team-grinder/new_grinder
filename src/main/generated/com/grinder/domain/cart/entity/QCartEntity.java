package com.grinder.domain.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCartEntity is a Querydsl query type for CartEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartEntity extends EntityPathBase<CartEntity> {

    private static final long serialVersionUID = 82689584L;

    public static final QCartEntity cartEntity = new QCartEntity("cartEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOrdered = createBoolean("isOrdered");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QCartEntity(String variable) {
        super(CartEntity.class, forVariable(variable));
    }

    public QCartEntity(Path<? extends CartEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCartEntity(PathMetadata metadata) {
        super(CartEntity.class, metadata);
    }

}

