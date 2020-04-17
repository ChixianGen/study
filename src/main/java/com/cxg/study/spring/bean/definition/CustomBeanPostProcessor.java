package com.cxg.study.spring.bean.definition;

import com.cxg.study.utils.Conts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Date: 2020/1/13 10:25
 * @Description: 自定义bean后置处理器，默认会对整个Spring容器中所有的bean进行处理 .
 * @Copyright: All rights reserved.
 */
@Slf4j
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains(Conts.BEAN_DEFINITION_ENTITY)) {
            BeanDefinitionEntity entity = (BeanDefinitionEntity) bean;
            log.debug("postProcessBeforeInitialization - name - {}", entity.getName());
            String before = entity.hello("before");
            entity.setName(before);
            return entity;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains(Conts.BEAN_DEFINITION_ENTITY)) {
            BeanDefinitionEntity entity = (BeanDefinitionEntity) bean;
            log.debug("postProcessAfterInitialization - name - {}", entity.getName());
        }
        return bean;
    }
}
