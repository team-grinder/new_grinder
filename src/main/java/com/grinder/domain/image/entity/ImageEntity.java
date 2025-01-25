package com.grinder.domain.image.entity;


import com.grinder.common.annotation.Name;
import com.grinder.domain.image.model.CompressType;
import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ImageEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String imageName;

    private String url;

    private String fileSize;

    @Name(name = "파일 정보")
    private ContentType contentType;

    @Name(name = "해당 파일 관련 Entity ID")
    private Long contentId;

    private CompressType compressType;
}
