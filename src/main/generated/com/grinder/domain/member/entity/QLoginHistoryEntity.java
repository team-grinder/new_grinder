package com.grinder.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginHistoryEntity is a Querydsl query type for LoginHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginHistoryEntity extends EntityPathBase<LoginHistoryEntity> {

    private static final long serialVersionUID = 2103897525L;

    public static final QLoginHistoryEntity loginHistoryEntity = new QLoginHistoryEntity("loginHistoryEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath deviceInfo = createString("deviceInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final EnumPath<com.grinder.domain.member.model.LoginType> loginType = createEnum("loginType", com.grinder.domain.member.model.LoginType.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QLoginHistoryEntity(String variable) {
        super(LoginHistoryEntity.class, forVariable(variable));
    }

    public QLoginHistoryEntity(Path<? extends LoginHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginHistoryEntity(PathMetadata metadata) {
        super(LoginHistoryEntity.class, metadata);
    }

}

