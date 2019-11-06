package com.cxg.study.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: cxg
 * @Description: 获取spring的applicationContext上下文 .
 * @Date: 2019/10/30 22:35
 * @Copyright: All rights reserved.
 */
@Component
public class ApplicationContextDemo {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void after() {
        System.out.println(applicationContext.getId());
        System.out.println(applicationContext.getApplicationName());
    }
}
