package com.dyman.im.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalTime;

/**
 * Create date 2017/5/10
 * <p>时间转换 java8接口</p>
 *
 * @author Males1
 */
public class LocalTimeConverter extends BidirectionalConverter<LocalTime, LocalTime> {
    @Override
    public LocalTime convertTo(LocalTime localTime, Type<LocalTime> type, MappingContext mappingContext) {
        return LocalTime.from(localTime);
    }

    @Override
    public LocalTime convertFrom(LocalTime localTime, Type<LocalTime> type, MappingContext mappingContext) {
        return LocalTime.from(localTime);
    }
}
