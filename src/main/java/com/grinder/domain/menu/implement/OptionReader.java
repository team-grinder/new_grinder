package com.grinder.domain.menu.implement;

import com.grinder.domain.menu.entity.OptionEntity;
import com.grinder.domain.menu.model.Option;
import com.grinder.domain.menu.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptionReader {
    private final OptionRepository optionRepository;

    public List<Option> readOptions(List<Long> menuIds) {
        return optionRepository.findAllByMenuIdIn(menuIds).stream()
                .map(OptionEntity::toOption).collect(Collectors.toList());
    }
}
