package com.dyman.im.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;

/**
 * Create date 2017/5/10
 * <p>本地日期转换</p>
 *
 * @author Males1
 */
public class LocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {
    @Override
    public LocalDate convertTo(LocalDate localDate, Type<LocalDate> type, MappingContext mappingContext) {
        return LocalDate.from(localDate);
    }

    @Override
    public LocalDate convertFrom(LocalDate localDate, Type<LocalDate> type, MappingContext mappingContext) {
        return LocalDate.from(localDate);
    }
}
