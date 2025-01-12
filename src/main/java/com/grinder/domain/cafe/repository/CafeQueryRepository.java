package com.grinder.domain.cafe.repository;

import com.grinder.domain.cafe.entity.CafeEntity;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.grinder.domain.cafe.entity.QCafeEntity.cafeEntity;
import static com.grinder.domain.order.entity.QOrder.order;

@Repository
public class CafeQueryRepository {
    private final JPAQueryFactory query;

    public CafeQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<CafeEntity> findPopularCafe() {
        List<Tuple> cafeAndOrderRank = query
                .from(cafeEntity)
                .select(cafeEntity, order.count())
                .leftJoin(order).on(cafeEntity.id.eq(order.cafeId))
                .orderBy(order.count().desc())
                .groupBy(cafeEntity)
                .limit(3)
                .fetch();

        return cafeAndOrderRank.stream()
                .map(tuple -> tuple.get(cafeEntity))
                .collect(Collectors.toList());
    }
}
