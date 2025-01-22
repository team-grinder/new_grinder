package com.grinder.domain.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartDetailEntity is a Querydsl query type for CartDetailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartDetailEntity extends EntityPathBase<CartDetailEntity> {

    private static final long serialVersionUID = 437769249L;

    public static final QCartDetailEntity cartDetailEntity = new QCartDetailEntity("cartDetailEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cartId = createNumber("cartId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<com.grinder.domain.menu.model.Option, SimplePath<com.grinder.domain.menu.model.Option>> options = this.<com.grinder.domain.menu.model.Option, SimplePath<com.grinder.domain.menu.model.Option>>createList("options", com.grinder.domain.menu.model.Option.class, SimplePath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QCartDetailEntity(String variable) {
        super(CartDetailEntity.class, forVariable(variable));
    }

    public QCartDetailEntity(Path<? extends CartDetailEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCartDetailEntity(PathMetadata metadata) {
        super(CartDetailEntity.class, metadata);
    }

}

