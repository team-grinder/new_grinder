package com.grinder.common.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Local Date Time 변환 로직
@Converter(autoApply = true)
public class LclDtConverter implements AttributeConverter<LocalDateTime, String> {
    private static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(SAVE_FORMATTER);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        // 데이터베이스에서 가져온 형식(YYYYMMDDHH24MISS)을 LocalDateTime으로 변환
        return LocalDateTime.parse(dbData, SAVE_FORMATTER);
    }
}