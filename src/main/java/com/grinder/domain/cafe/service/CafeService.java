package com.grinder.domain.cafe.service;

import com.grinder.domain.cafe.implement.CafeReader;
import com.grinder.domain.cafe.model.Cafe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {
    private final CafeReader cafeReader;

    public Cafe getCafe(Long cafeId) {
        return cafeReader.read(cafeId);
    }
}
