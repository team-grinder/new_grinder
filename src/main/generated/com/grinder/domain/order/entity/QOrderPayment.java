package com.grinder.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderPayment is a Querydsl query type for OrderPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderPayment extends EntityPathBase<OrderPayment> {

    private static final long serialVersionUID = -975220941L;

    public static final QOrderPayment orderPayment = new QOrderPayment("orderPayment");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QOrderPayment(String variable) {
        super(OrderPayment.class, forVariable(variable));
    }

    public QOrderPayment(Path<? extends OrderPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderPayment(PathMetadata metadata) {
        super(OrderPayment.class, metadata);
    }

}

