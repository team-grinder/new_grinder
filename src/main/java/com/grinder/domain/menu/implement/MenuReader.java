package com.grinder.domain.menu.implement;

import com.grinder.domain.menu.entity.MenuEntity;
import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MenuReader {
    private final MenuRepository menuRepository;

    public Menu read(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.")
        ).toMenu();
    }

    public List<Menu> readAll(List<Long> menuIds) {
        return menuRepository.findAllById(menuIds).stream().map(MenuEntity::toMenu).collect(Collectors.toList());
    }
}
