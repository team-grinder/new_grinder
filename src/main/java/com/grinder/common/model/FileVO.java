package com.grinder.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class FileVO {
    private File file;

    private String fileName;

    private long fileSize;

    private String contentType;
    public FileVO(File file, String contentType) {
        this.file = file;
        this.fileName = file.getName();
        this.fileSize = file.length();
        this.contentType = contentType;
    }
}
