package com.dyman.im.base;

import com.dyman.im.mybatisplus.TimeUtils;
import com.dyman.im.util.StringKit;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return StringKit.trim(source).map(TimeUtils::parseLocalDateTimeFromString).orElse(null);
    }
}
