package com.grinder.domain.menu.implement;

import com.grinder.domain.menu.entity.OptionEntity;
import com.grinder.domain.menu.model.Option;
import com.grinder.domain.menu.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MenuManager {
    private final OptionRepository optionRepository;

    public List<Option> getOptions(Long menuId) {
        return optionRepository.findAllByMenuIdOrderBySequence(menuId).stream()
                .map(OptionEntity::toOption)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addOption(Long menuId, String optionName, Long price, int sequence) {
        optionRepository.save(OptionEntity.builder()
                .menuId(menuId)
                .name(optionName)
                .price(price)
                .sequence(sequence)
                .build());
    }

    @Transactional
    public void addOptions(Long menuId, List<Option> options) {
        for (Option option : options) {
            addOption(menuId, option.getName(), option.getPrice(), option.getSequence());
        }
    }
}
