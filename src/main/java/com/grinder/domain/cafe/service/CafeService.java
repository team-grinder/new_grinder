package com.grinder.domain.cafe.service;

import com.grinder.domain.cafe.implement.CafeManager;
import com.grinder.domain.cafe.implement.CafeReader;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeAndMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {
    private final CafeReader cafeReader;
    private final CafeManager cafeManager;

    public Cafe getCafe(Long cafeId) {
        return cafeReader.read(cafeId);
    }

    public CafeAndMenu getCafeAndMenu(Long cafeId) {
        return cafeManager.getCafeAndMenu(cafeId);
    }

    public List<Cafe> findCafeByName(String name) {
        return cafeReader.readByName(name);
    }

    public Cafe createCafe(String name, String address, String description, String tel, String businessNumber) {
        return cafeReader.createCafe(name, address, description, tel, businessNumber);
    }
}
