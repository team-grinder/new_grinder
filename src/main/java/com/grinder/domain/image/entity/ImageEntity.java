package com.grinder.domain.image.entity;


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

    private Long contentId;

    private ContentType contentType;
}
