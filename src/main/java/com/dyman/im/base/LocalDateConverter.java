package com.dyman.im.base;

import com.dyman.im.mybatisplus.TimeUtils;
import com.dyman.im.util.StringKit;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

final class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return StringKit.trim(source).map(TimeUtils::parseLocalDateFromString).orElse(null);
    }
}
