package com.grinder.domain.cafe.implement;

import com.grinder.common.model.Pages;
import com.grinder.domain.cafe.entity.CafeEntity;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.model.CafeSearchPage;
import com.grinder.domain.cafe.repository.CafeQueryRepository;
import com.grinder.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CafeReader {
    private final CafeRepository cafeRepository;
    private final CafeQueryRepository cafeQueryRepository;

    public List<Cafe> findPopularCafe() {
        return cafeQueryRepository.findPopularCafe().stream()
                .map(CafeEntity::toCafe).collect(Collectors.toList());
    }

    public Pages<Cafe> findCafePage(CafeSearchPage searchPage) {
        return cafeQueryRepository.findCafePage(searchPage);
    }

    public Cafe read(Long id) {
        return cafeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카페가 존재하지 않습니다.")
        ).toCafe();
    }

    public List<Cafe> readByName(String name) {
        List<CafeEntity> allByName = cafeRepository.findAllByName(name);
        if (allByName.isEmpty()) {
            throw new IllegalArgumentException("해당 이름의 카페가 존재하지 않습니다.");
        }
        return allByName.stream().map(CafeEntity::toCafe).collect(Collectors.toList());
    }

    public Cafe createCafe(CafeCreate request) {
        CafeEntity cafeEntity = new CafeEntity(
                request.getName(),
                request.getAddress(),
                request.getDescription(),
                request.getTel(),
                request.getBusinessNumber()
        );
        return cafeRepository.save(cafeEntity).toCafe();
    }

    public void deleteCafe(Long cafeId) {
        CafeEntity cafeEntity = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페가 존재하지 않습니다.")
        );
        cafeRepository.delete(cafeEntity);
    }
}