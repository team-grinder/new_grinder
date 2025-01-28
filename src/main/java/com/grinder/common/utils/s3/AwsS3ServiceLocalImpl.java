package com.grinder.common.utils.s3;

import com.ulisesbocchio.jasyptspringboot.annotation.ConditionalOnMissingBean;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.UUID;

@Service
@Profile("local")
@AllArgsConstructor
public class AwsS3ServiceLocalImpl implements AwsS3Service {
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        return createFileName(multipartFile.getOriginalFilename());
    }

    @Override
    public String uploadFile(File file, String contentType) {
        return createFileName(file.getName());
    }

    @Override
    public String generatePresignedUrl(String fileName) {
        return "https://cdn.vuetifyjs.com/images/parallax/material.jpg";
    }

    @Override
    public void deleteFile(String fileName) {
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
