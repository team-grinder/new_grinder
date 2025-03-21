package com.grinder.domain.member.repository;

import com.grinder.common.model.Pages;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.member.entity.SystemAdminEntity;
import com.grinder.domain.member.model.SystemAdmin;
import com.grinder.domain.member.model.SystemAdminSearchPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.grinder.domain.member.entity.QSystemAdminEntity.systemAdminEntity;

@Repository
public class SystemAdminQueryRepository {
    private final JPAQueryFactory query;

    public SystemAdminQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Pages<SystemAdmin> getSystemAdmins(SystemAdminSearchPage searchPage) {
        // 날짜가 null이 아니라면 startDate와 endDate를 기준으로 검색
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 날짜 범위 검색
        searchForDateRange(searchPage, booleanBuilder);

        // 검색 타입 및 검색어 검색
        SearchForType(searchPage, booleanBuilder);

        Long systemAdminCount = query.from(systemAdminEntity)
                .select(systemAdminEntity.count())
                .where(booleanBuilder)
                .fetchOne();

        // 페이지네이션 처리
        List<SystemAdmin> systemAdmins = query.from(systemAdminEntity)
                .select(systemAdminEntity)
                .where(booleanBuilder)
                .offset(searchPage.getPage() * searchPage.getSize())
                .limit(searchPage.getSize())
                .fetch()
                .stream()
                .map(SystemAdminEntity::toSystemAdmin)
                .collect(Collectors.toList());

        return Pages.create(
                systemAdmins,
                systemAdminCount == null ? 0 : systemAdminCount,
                searchPage.getPage(),
                searchPage.getSize(),
                5
        );
    }

    private void SearchForType(SystemAdminSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getSearchType() == null) return; // 검색 타입이 null인 경우는 검색하지 않음

        switch (searchPage.getSearchType()) {
            case ALL:
                booleanBuilder.and(systemAdminEntity.email.contains(searchPage.getSearchQuery())
                        .or(systemAdminEntity.nickname.contains(searchPage.getSearchQuery())));
                break;
            case EMAIL:
                booleanBuilder.and(systemAdminEntity.email.contains(searchPage.getSearchQuery()));
                break;
            case NICKNAME:
                booleanBuilder.and(systemAdminEntity.nickname.contains(searchPage.getSearchQuery()));
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 검색 방식입니다.");
        }
    }

    private void searchForDateRange(SystemAdminSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getStartDate() != null && searchPage.getEndDate() != null) {
            LocalDateTime[] betweenRange = DateUtils.parseForStartAndEndDate(searchPage.getStartDate(), searchPage.getEndDate());
            booleanBuilder.and(systemAdminEntity.createDate.between(betweenRange[0], betweenRange[1]));
        }
    }
}
