package com.grinder.domain.image.implement;

import com.grinder.common.model.FileVO;
import com.grinder.common.utils.ImageUtils;
import com.grinder.common.utils.s3.AwsS3Service;
import com.grinder.domain.image.entity.ImageEntity;
import com.grinder.domain.image.model.CompressType;
import com.grinder.domain.image.model.ImageInfo;
import com.grinder.domain.image.repository.ImageRepository;
import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ImageReader {
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public boolean createImage(List<MultipartFile> multipartFiles, ContentType contentType, Long contentId) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            // s3 이미지 저장 로직
            String uploadFileKey = awsS3Service.uploadFile(multipartFile);

            // 완료 됐다면 DB에 이미지 정보 저장
            ImageInfo imageInfo = ImageInfo.builder()
                    .originalFileName(multipartFile.getOriginalFilename())
                    .storedFileName(uploadFileKey)
                    .fileSize(multipartFile.getSize())
                    .fileType(multipartFile.getContentType())
                    .build();

            imageInfoList.add(imageInfo);
        }

        saveImageInfo(contentType, contentId, imageInfoList);

        return true;
    }

    @Transactional
    public void createAsyncImage(List<FileVO> files, ContentType contentType, Long contentId) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        for (FileVO fileVO : files) {
            File file = fileVO.getFile();
            // s3 이미지 저장 로직
            String uploadFileKey = awsS3Service.uploadFile(file, fileVO.getContentType());

            // 완료 됐다면 DB에 이미지 정보 저장
            ImageInfo imageInfo = ImageInfo.builder()
                    .originalFileName(fileVO.getFileName())
                    .storedFileName(uploadFileKey)
                    .fileSize(fileVO.getFileSize())
                    .fileType(fileVO.getContentType())
                    .build();

            imageInfoList.add(imageInfo);
        }

        saveImageInfo(contentType, contentId, imageInfoList);
    }



    // 압축 이미지 저장
    public boolean compressAsyncImage(List<MultipartFile> multipartFiles, ContentType contentType, Long contentId) {
        List<CompressType> compressTypes = CompressType.of(contentType);

        // 압축 후 저장
        CompletableFuture<List<FileVO>> compressedFiles =
                ImageUtils.asyncCompressImage(multipartFiles, compressTypes, 0.75f);

        compressedFiles.thenAccept(files -> {
            createAsyncImage(files, contentType, contentId);
        });

        return true;
    }


    private void saveImageInfo(ContentType contentType, Long contentId, List<ImageInfo> imageInfoList) {
        try {
            List<ImageEntity> imageEntities = imageInfoList.stream()
                    .map(info -> new ImageEntity(
                            info.getOriginalFileName(),
                            info.getStoredFileName(),
                            info.getFileSize(),
                            contentType,
                            contentId,
                            CompressType.ORIGINAL)
                    ).collect(Collectors.toList());

            imageRepository.saveAll(imageEntities);
        } catch (Exception e) {
            log.error("이미지 저장 오류 : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지 저장에 실패했습니다.");
        }
    }
}
