package com.grinder.domain.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptionEntity is a Querydsl query type for OptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionEntity extends EntityPathBase<OptionEntity> {

    private static final long serialVersionUID = -835769020L;

    public static final QOptionEntity optionEntity = new QOptionEntity("optionEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath lockYn = createBoolean("lockYn");

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QOptionEntity(String variable) {
        super(OptionEntity.class, forVariable(variable));
    }

    public QOptionEntity(Path<? extends OptionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptionEntity(PathMetadata metadata) {
        super(OptionEntity.class, metadata);
    }

}

