package com.cxg.study.spring.bean.definition;

import com.cxg.study.utils.Conts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Date: 2020/1/13 10:07
 * @Description: 测试spring-bean定义的实体类 .
 * @Copyright: All rights reserved.
 */
@Slf4j
@Data
@Component(Conts.BEAN_DEFINITION_ENTITY)
public class BeanDefinitionEntity {

    private String name = "init";

    public String hello(String target) {
        return "hello " + target;
    }

    public void init() {
        log.debug("BeanDefinitionEntity 执行 init() 方法");
    }
}
