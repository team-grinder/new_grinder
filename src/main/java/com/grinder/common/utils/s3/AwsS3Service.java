package com.grinder.common.utils.s3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@Service
public interface AwsS3Service {
    String uploadFile(MultipartFile multipartFile);

    String uploadFile(File file, String contentType);

    String uploadFile(InputStream inputStream, String fileName, String contentType);

    // 서명된 URL 생성
    String generatePresignedUrl(String fileName);

    void deleteFile(String fileName);
}
