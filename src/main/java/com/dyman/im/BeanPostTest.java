package com.dyman.im;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author dyman
 * @describe
 * @date 2020/5/29
 */
@Component
public class BeanPostTest implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("装载bean："+ beanName);
        return bean;
    }
}
