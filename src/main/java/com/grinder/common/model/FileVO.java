package com.grinder.common.model;

import com.grinder.domain.image.model.CompressType;
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

    private CompressType compressType;

    public FileVO(File file, String contentType, CompressType compressType) {
        this.file = file;
        this.fileName = file.getName().replace("temp_", "");
        this.fileSize = file.length();
        this.contentType = contentType;
        this.compressType = compressType;
    }

    public String getFileName() {
        return fileName.replace("temp_", "");
    }
}
