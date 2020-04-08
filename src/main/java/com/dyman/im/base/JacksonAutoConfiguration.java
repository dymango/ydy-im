package com.dyman.im.base;

import com.dyman.im.constant.MessageTypeEnum;
import com.dyman.im.mybatisplus.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Log4j2
@ConditionalOnClass(ObjectMapper.class)
@Configuration
public class JacksonAutoConfiguration {
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jdk8Module.class)
    @Bean
    public Jdk8Module jdk8Module() {
        return new Jdk8Module();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public SimpleModule localDateTimeModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));
//        module.addSerializer(Enum.class, new EnumSerialzer());
//        module.addDeserializer(MessageTypeEnum.class, new EnumDeSerialzer());
        return module;
    }

    @ConditionalOnProperty(prefix = "zhdf", name = "convertNullToEmpty", havingValue = "true", matchIfMissing = true)
    @Bean
    public BeanPostProcessor objectMapperBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof ObjectMapper) {
                }
                return bean;
            }
        };
    }
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomizer() {
//        return m -> {
////            m.serializerByType(Enum.class, EnumSerialzer.getInstance());
////            m.deserializerByType(Enum.class, new EnumDeSerialzer());
//            m.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        };
//    }
}
