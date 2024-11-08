package com.grinder.domain.menu.implement;

import com.grinder.domain.menu.model.MenuInfo;
import com.grinder.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuReader {
    private final MenuRepository menuRepository;

    public MenuInfo read(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.")
        ).toMenuInfo();
    }


}
