package com.grinder.domain.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuEntity is a Querydsl query type for MenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuEntity extends EntityPathBase<MenuEntity> {

    private static final long serialVersionUID = 419403182L;

    public static final QMenuEntity menuEntity = new QMenuEntity("menuEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final NumberPath<Long> cafeId = createNumber("cafeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final BooleanPath lockYn = createBoolean("lockYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final BooleanPath seasonYn = createBoolean("seasonYn");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QMenuEntity(String variable) {
        super(MenuEntity.class, forVariable(variable));
    }

    public QMenuEntity(Path<? extends MenuEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuEntity(PathMetadata metadata) {
        super(MenuEntity.class, metadata);
    }

}

