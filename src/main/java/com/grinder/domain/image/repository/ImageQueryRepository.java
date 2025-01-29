package com.grinder.domain.image.repository;

import com.grinder.domain.image.entity.QImageEntity;
import com.grinder.domain.image.model.CompressType;
import com.grinder.domain.image.model.ImageTag;
import com.grinder.domain.like.model.ContentType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;

@Repository
public class ImageQueryRepository {
    private final JPAQueryFactory query;

    public ImageQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Map<Long, ImageTag> findImageByFeedIds(List<Long> feedIds) {
        QImageEntity image = QImageEntity.imageEntity;

        return query
                .from(image)
                .where(
                        image.contentType.eq(ContentType.FEED)
                                .and(image.compressType.eq(CompressType.MEDIUM))
                                .and(image.contentId.in(feedIds))
                )
                .transform(groupBy(image.contentId).as(
                        Projections.constructor(
                                ImageTag.class,
                                image.imageName,
                                image.imageKey
                        )
                ));
    }
}
