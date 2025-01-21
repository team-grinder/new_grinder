package com.grinder.domain.seller.apply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSellerApply is a Querydsl query type for SellerApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSellerApply extends EntityPathBase<SellerApply> {

    private static final long serialVersionUID = -1517326781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerApply sellerApply = new QSellerApply("sellerApply");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    public final com.grinder.domain.cafe.entity.QCafeEntity cafeEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.grinder.domain.member.entity.QMemberEntity memberEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath regImageUrl = createString("regImageUrl");

    public QSellerApply(String variable) {
        this(SellerApply.class, forVariable(variable), INITS);
    }

    public QSellerApply(Path<? extends SellerApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSellerApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSellerApply(PathMetadata metadata, PathInits inits) {
        this(SellerApply.class, metadata, inits);
    }

    public QSellerApply(Class<? extends SellerApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cafeEntity = inits.isInitialized("cafeEntity") ? new com.grinder.domain.cafe.entity.QCafeEntity(forProperty("cafeEntity")) : null;
        this.memberEntity = inits.isInitialized("memberEntity") ? new com.grinder.domain.member.entity.QMemberEntity(forProperty("memberEntity")) : null;
    }

}

