package com.grinder.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderMenuInfo is a Querydsl query type for OrderMenuInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderMenuInfo extends EntityPathBase<OrderMenuInfo> {

    private static final long serialVersionUID = 2141105952L;

    public static final QOrderMenuInfo orderMenuInfo = new QOrderMenuInfo("orderMenuInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public QOrderMenuInfo(String variable) {
        super(OrderMenuInfo.class, forVariable(variable));
    }

    public QOrderMenuInfo(Path<? extends OrderMenuInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderMenuInfo(PathMetadata metadata) {
        super(OrderMenuInfo.class, metadata);
    }

}

