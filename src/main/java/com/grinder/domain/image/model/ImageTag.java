package com.grinder.domain.image.model;

import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ImageTag {
    private String imageName;

    private String imageKey;

    private ContentType contentType;

    @Setter
    private String imageUrl;

    private int sequence;

    public ImageTag(String imageName, String imageKey, ContentType contentType, int sequence) {
        this.imageName = imageName;
        this.imageKey = imageKey;
        this.contentType = contentType;
        this.sequence = sequence;
    }
}
