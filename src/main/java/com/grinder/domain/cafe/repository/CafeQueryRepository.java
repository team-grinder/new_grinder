package com.grinder.domain.cafe.repository;

import com.grinder.common.model.Pages;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.cafe.entity.CafeEntity;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeSearchPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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

    public Pages<Cafe> findCafePage(CafeSearchPage searchPage) {
        // 날짜가 null이 아니라면 startDate와 endDate를 기준으로 검색
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 날짜 범위 검색
        searchForDateRange(searchPage, booleanBuilder);

        // 검색 타입 및 검색어 검색
        SearchForType(searchPage, booleanBuilder);

        Long cafeCount = query.from(cafeEntity)
                .select(cafeEntity.count())
                .where(booleanBuilder)
                .fetchOne();

        List<CafeEntity> cafeEntities = query.from(cafeEntity)
                .select(cafeEntity)
                .where(booleanBuilder)
                .orderBy(cafeEntity.createDate.desc())
                .offset(searchPage.getPage() * searchPage.getSize())
                .limit(searchPage.getSize())
                .fetch();

        return Pages.create(
                cafeEntities.stream().map(CafeEntity::toCafe).collect(Collectors.toList()),
                cafeCount != null ? cafeCount : 0,
                searchPage.getPage(),
                searchPage.getSize(),
                5
        );
    }

    private void SearchForType(CafeSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getSearchType() == null) return; // 검색 타입이 null인 경우는 검색하지 않음

        switch (searchPage.getSearchType()) {
            case ALL:
                booleanBuilder.and(cafeEntity.name.contains(searchPage.getSearchQuery())
                        .or(cafeEntity.address.contains(searchPage.getSearchQuery())));
                break;
            case NAME:
                booleanBuilder.and(cafeEntity.name.contains(searchPage.getSearchQuery()));
                break;
            case ADDRESS:
                booleanBuilder.and(cafeEntity.address.contains(searchPage.getSearchQuery()));
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 검색 방식입니다.");
        }
    }

    private void searchForDateRange(CafeSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getStartDate() != null && searchPage.getEndDate() != null) {
            LocalDateTime[] betweenRange = DateUtils.parseForStartAndEndDate(searchPage.getStartDate(), searchPage.getEndDate());
            booleanBuilder.and(cafeEntity.createDate.between(betweenRange[0], betweenRange[1]));
        }
    }
}
