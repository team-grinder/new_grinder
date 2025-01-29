package com.grinder.domain.image.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ImageTag {
    private String imageName;

    private String imageKey;

    @Setter
    private String imageUrl;
}
