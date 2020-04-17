package com.cxg.study.spring.bean.definition;

import com.cxg.study.utils.Conts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Date: 2020/1/13 10:11
 * @Description: 自定义的bean注册后置处理器 .
 * @Copyright: All rights reserved.
 */
@Slf4j
@Component
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition chixiangen = beanFactory.getBeanDefinition(Conts.BEAN_DEFINITION_ENTITY);
        log.debug("beanClassName - {}  scope - {}", chixiangen.getBeanClassName(), chixiangen.getScope());
//        chixiangen.setScope(BeanDefinition.SCOPE_PROTOTYPE);
//        log.debug("scope - {}", chixiangen.getScope());
        chixiangen.setInitMethodName("init");
    }
}
