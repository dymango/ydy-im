package com.dyman.im.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.ZonedDateTime;

/**
 * Create date 2017/5/10
 * <p>本地日期ZonedDateTime转换 java8 接口</p>
 *
 * @author Males1
 */
public class ZonedDateTimeConverter extends BidirectionalConverter<ZonedDateTime, ZonedDateTime> {
    @Override
    public ZonedDateTime convertTo(ZonedDateTime odt, Type<ZonedDateTime> type, MappingContext mappingContext) {
        return ZonedDateTime.from(odt);
    }

    @Override
    public ZonedDateTime convertFrom(ZonedDateTime odt, Type<ZonedDateTime> type, MappingContext mappingContext) {
        return ZonedDateTime.from(odt);
    }
}
