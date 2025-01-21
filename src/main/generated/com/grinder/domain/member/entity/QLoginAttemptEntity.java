package com.grinder.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginAttemptEntity is a Querydsl query type for LoginAttemptEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginAttemptEntity extends EntityPathBase<LoginAttemptEntity> {

    private static final long serialVersionUID = -1874817746L;

    public static final QLoginAttemptEntity loginAttemptEntity = new QLoginAttemptEntity("loginAttemptEntity");

    public final com.grinder.common.entity.QBaseDateEntity _super = new com.grinder.common.entity.QBaseDateEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> failCount = createNumber("failCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isLocked = createBoolean("isLocked");

    public final DateTimePath<java.time.LocalDateTime> lockedTime = createDateTime("lockedTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QLoginAttemptEntity(String variable) {
        super(LoginAttemptEntity.class, forVariable(variable));
    }

    public QLoginAttemptEntity(Path<? extends LoginAttemptEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginAttemptEntity(PathMetadata metadata) {
        super(LoginAttemptEntity.class, metadata);
    }

}

