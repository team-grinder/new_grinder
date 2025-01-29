package com.grinder.domain.cafe.implement;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeAndMenu;
import com.grinder.domain.image.implement.ImageReader;
import com.grinder.domain.image.model.ImageTag;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.menu.implement.MenuReader;
import com.grinder.domain.menu.model.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeManager {
    private final CafeReader cafeReader;
    private final MenuReader menuReader;
    private final ImageReader imageReader;

    public List<Cafe> findPopularCafe() {
        return cafeReader.findPopularCafe();
    }

    public CafeAndMenu getCafeAndMenu(Long cafeId) {
        Cafe cafe = cafeReader.read(cafeId);

        List<ImageTag> imageList = imageReader.findImageByContentId(
                cafeId,
                ContentType.CAFE_LOGO, ContentType.CAFE_IMAGE);

        List<Menu> menus = menuReader.readAllMenu(cafeId);

        return CafeAndMenu.builder()
                .name(cafe.getName())
                .address(cafe.getAddress())
                .imageUrl(imageList.stream().filter(tag -> tag.getContentType() == ContentType.CAFE_IMAGE)
                        .findFirst().map(ImageTag::getImageUrl).orElse(""))
                .logoUrl(imageList.stream().filter(tag -> tag.getContentType() == ContentType.CAFE_LOGO)
                        .findFirst().map(ImageTag::getImageUrl).orElse(""))
                .phoneNum(cafe.getTel())
                .averageGrade(cafe.getAverageGrade())
                .menus(menus)
                .build();
    }
}
