package com.grinder.common.utils;

import com.grinder.common.model.FileVO;
import com.grinder.domain.image.model.CompressType;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ImageUtils {
    /**
     * MultipartFile 이미지 압축 및 크기 조정
     *
     * @param imageFile          MultipartFile 객체 (업로드된 이미지)
     * @param outputFile         압축된 이미지가 저장될 파일 경로
     * @param compressType       압축 타입 (크기 및 suffix 관리)
     * @param compressionQuality 압축 품질 (0.0 ~ 1.0)
     * @throws IOException 파일 처리 중 오류
     */
    public static File compressImage(MultipartFile imageFile, File outputFile, CompressType compressType, float compressionQuality) throws IOException {
        // 1. MultipartFile을 임시 파일로 복사 (이미지 사용 시 임시 파일이 삭제 될 수 있으므로 복사하여 사용)
        File tempFile = File.createTempFile("image_", imageFile.getOriginalFilename());
        imageFile.transferTo(tempFile); // MultipartFile을 임시 파일로 저장

        try {
            // 2. BufferedImage로 변환
            BufferedImage image = ImageIO.read(tempFile);

            // 3. Thumbnails를 이용해 이미지 압축 및 크기 조정
            compress(outputFile, compressType, compressionQuality, image);

            tempFile.deleteOnExit();
        } catch (IOException e) {
            log.error("이미지 압축 및 크기 조정 중 오류 발생", e);
            throw new IOException("이미지 압축 및 크기 조정 중 오류 발생", e);
        } finally {
            // 4. 임시 파일 삭제
            if (tempFile.exists() && !tempFile.delete()) {
                log.error("임시 파일 삭제 실패: {}", tempFile.getAbsolutePath());
            }
        }

        return outputFile;
    }

    public static File compressImage(File inputFile, File outputFile, CompressType compressType, float compressionQuality) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) {
            throw new IOException("이미지 읽기 실패: " + inputFile.getAbsolutePath());
        }
        // Thumbnails 라이브러리를 사용한 압축 및 크기 조정 예시
        Thumbnails.of(image)
                .scale(compressType.getScale())
                .outputQuality(compressionQuality)
                .toFile(outputFile);
        return outputFile;
    }

    public static CompletableFuture<List<FileVO>> asyncCompressImage(List<MultipartFile> imageFiles, List<CompressType> compressType, float compressionQuality) {
        return CompletableFuture.supplyAsync(() -> {
            List<FileVO> fileVOList = new ArrayList<>();
            AtomicInteger sequence = new AtomicInteger(1);

            imageFiles.forEach(imageFile -> {
                sequence.getAndIncrement();
                for (CompressType type : compressType) {
                    try {
                        File outputFile = File.createTempFile("temp_", imageFile.getOriginalFilename() + type.getSuffix());

                        File file = compressImage(imageFile, outputFile, type, compressionQuality);
                        fileVOList.add(new FileVO(file, imageFile.getContentType(), type, sequence.get()));

                        outputFile.deleteOnExit();
                    } catch (IOException e) {
                        log.error("이미지 압축 및 크기 조정 중 오류 발생", e);
                        throw new RuntimeException("이미지 압축 및 크기 조정 중 오류 발생", e);
                    }
                }
            });
            return fileVOList;
        });
    }

    private static void compress(File outputFile, CompressType compressType, float compressionQuality, BufferedImage image) throws IOException {
        Thumbnails.of(image)
                .scale(compressType.getScale())
                .outputQuality(compressionQuality)
                .allowOverwrite(true)
                .toFile(outputFile);
    }
}
