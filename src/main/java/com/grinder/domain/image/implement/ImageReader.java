package com.grinder.domain.image.implement;

import com.grinder.common.model.FileVO;
import com.grinder.common.utils.ImageUtils;
import com.grinder.common.utils.s3.AwsS3Service;
import com.grinder.domain.image.entity.ImageEntity;
import com.grinder.domain.image.model.CompressType;
import com.grinder.domain.image.model.ImageInfo;
import com.grinder.domain.image.model.ImageTag;
import com.grinder.domain.image.repository.ImageQueryRepository;
import com.grinder.domain.image.repository.ImageRepository;
import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.grinder.common.utils.ImageUtils.compressImage;

@Slf4j
@Component
@AllArgsConstructor
public class ImageReader {
    private final ImageRepository imageRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final AwsS3Service awsS3Service;

    public Map<Long, List<ImageTag>> findImageByFeedIds(List<Long> feedIds) {
        Map<Long, List<ImageTag>> imageByFeedIds = imageQueryRepository.findImageByFeedIds(feedIds);

        imageByFeedIds.forEach((k, v) ->
                v.forEach(imageTag ->
                        imageTag.setImageUrl(
                                awsS3Service.generatePresignedUrl(imageTag.getImageKey())))
        );

        return imageByFeedIds;
    }

    public List<ImageTag> findImageByContentId(Long contentId, ContentType... contentType) {
        List<ImageTag> imageTags = imageQueryRepository.findImageByContentId(contentId, contentType);

        imageTags.forEach(imageTag ->
                imageTag.setImageUrl(
                        awsS3Service.generatePresignedUrl(imageTag.getImageKey()))
        );

        return imageTags;
    }

    @Transactional
    public boolean createImage(List<MultipartFile> multipartFiles, ContentType contentType, Long contentId) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        int sequence = 1;

        for (MultipartFile multipartFile : multipartFiles) {
            try {
                byte[] fileData = multipartFile.getBytes();

                // S3 업로드를 위한 임시 InputStream 생성
                ByteArrayInputStream s3InputStream = new ByteArrayInputStream(fileData);
                String uploadFileKey = awsS3Service.uploadFile(s3InputStream, multipartFile.getOriginalFilename(), multipartFile.getContentType());

                // S3 업로드 후 DB 저장을 위한 ImageInfo 객체 생성
                ImageInfo imageInfo = ImageInfo.builder()
                        .originalFileName(multipartFile.getOriginalFilename())
                        .storedFileName(uploadFileKey)
                        .fileSize((long) fileData.length)
                        .fileType(multipartFile.getContentType())
                        .compressType(CompressType.ORIGINAL)
                        .sequence(sequence)
                        .build();
                imageInfoList.add(imageInfo);

                this.compressAsyncImage(fileData, multipartFile.getOriginalFilename(), multipartFile.getContentType(), contentType, contentId, sequence);

                sequence++;

            } catch (IOException e) {
                log.error("파일 처리 중 오류 발생: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 처리에 실패했습니다.");
            }
        }

        saveImageInfo(contentType, contentId, imageInfoList);
        return true;
    }

    public boolean compressAsyncImage(byte[] fileData, String originalFilename, String contentTypeStr, ContentType contentType, Long contentId, int sequence) {
        List<CompressType> compressTypes = CompressType.of(contentType);

        CompletableFuture<List<FileVO>> compressedFiles = CompletableFuture.supplyAsync(() -> {
            List<FileVO> fileVOList = new ArrayList<>();
            // 압축 타입(예: 여러 해상도나 품질 버전)에 대해 반복
            for (CompressType type : compressTypes) {
                try {
                    // 원본 데이터를 새 ByteArrayInputStream으로 복원하여 임시 파일 생성
                    File tempInputFile = File.createTempFile("compress_input_", originalFilename);
                    try (FileOutputStream fos = new FileOutputStream(tempInputFile)) {
                        fos.write(fileData);
                    }
                    // 압축 결과를 저장할 임시 파일 생성
                    String[] FileName = originalFilename.split("\\.");
                    File outputFile = File.createTempFile("compress_output_", FileName[0] + type.getSuffix() + "." + FileName[1]);

                    // 압축 수행 (여기서 compressImage는 tempInputFile을 읽어 outputFile에 압축 결과 저장)
                    File compressedFile = compressImage(tempInputFile, outputFile, type, 0.75f);
                    fileVOList.add(new FileVO(compressedFile, contentTypeStr, type, sequence));

                    // 임시 파일 삭제 예약
                    tempInputFile.delete();
                    outputFile.deleteOnExit();
                } catch (IOException e) {
                    log.error("이미지 압축 및 크기 조정 중 오류 발생", e);
                    throw new RuntimeException("이미지 압축 및 크기 조정 중 오류 발생", e);
                }
            }
            return fileVOList;
        });

        compressedFiles.thenAccept(files -> {
            createAsyncImage(files, contentType, contentId);
        });

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
                    .compressType(fileVO.getCompressType())
                    .build();

            imageInfoList.add(imageInfo);
        }

        saveImageInfo(contentType, contentId, imageInfoList);
    }

    public CompletableFuture<List<FileVO>> asyncCompressImage(List<MultipartFile> imageFiles, ContentType contentType) {
        List<CompressType> compressTypes = CompressType.of(contentType);

        return ImageUtils.asyncCompressImage(imageFiles, compressTypes, 0.75f);
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
                            info.getCompressType(),
                            info.getSequence())
                    ).collect(Collectors.toList());

            imageRepository.saveAll(imageEntities);
        } catch (Exception e) {
            log.error("이미지 저장 오류 : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지 저장에 실패했습니다.");
        }
    }
}
