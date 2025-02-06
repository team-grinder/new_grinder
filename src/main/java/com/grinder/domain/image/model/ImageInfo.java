package com.grinder.domain.image.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private String fileType;
    private CompressType compressType;
    private Integer sequence;

}
