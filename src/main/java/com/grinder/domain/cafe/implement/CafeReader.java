package com.grinder.domain.cafe.implement;

import com.grinder.domain.cafe.entity.CafeEntity;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CafeReader {
    private final CafeRepository cafeRepository;

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

    public Cafe createCafe(String name, String address, String description, String tel, String businessNumber) {
        CafeEntity cafeEntity = new CafeEntity(
                name,
                address,
                description,
                tel,
                businessNumber
        );
        return cafeRepository.save(cafeEntity).toCafe();
    }
}
