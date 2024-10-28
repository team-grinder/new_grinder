package com.grinder.menu.service;

import com.grinder.menu.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
}
