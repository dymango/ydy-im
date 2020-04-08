package com.dyman.im.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.Instant;

/**
 * Create date 2017/5/10
 * <p>实例转换</p>
 *
 * @author Males1
 */
public class InstantConverter extends BidirectionalConverter<Instant, Instant> {
    @Override
    public Instant convertTo(Instant instant, Type<Instant> type, MappingContext mappingContext) {
        return Instant.from(instant);
    }

    @Override
    public Instant convertFrom(Instant instant, Type<Instant> type, MappingContext mappingContext) {
        return Instant.from(instant);
    }
}
