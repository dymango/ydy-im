package com.dyman.im.base;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ConditionalOnClass(Converter.class)
@Configuration
class ConverterAutoConfiguration {
    @Bean
    Converter<String, LocalDateTime> localDateTimeConverter() {
        return new LocalDateTimeConverter();
    }

    @Bean
    Converter<String, LocalDate> localDateConverter() {
        return new LocalDateConverter();
    }
}