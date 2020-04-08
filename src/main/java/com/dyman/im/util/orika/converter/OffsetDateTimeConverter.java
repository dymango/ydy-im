package com.dyman.im.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.OffsetDateTime;

/**
 * Create date 2017/5/10
 * <p>本地日期OffsetDateTime转换</p>
 *
 * @author Males1
 */
public class OffsetDateTimeConverter extends BidirectionalConverter<OffsetDateTime, OffsetDateTime> {
    @Override
    public OffsetDateTime convertTo(OffsetDateTime zdt, Type<OffsetDateTime> type, MappingContext mappingContext) {
        return OffsetDateTime.from(zdt);
    }

    @Override
    public OffsetDateTime convertFrom(OffsetDateTime zdt, Type<OffsetDateTime> type, MappingContext mappingContext) {
        return OffsetDateTime.from(zdt);
    }
}
