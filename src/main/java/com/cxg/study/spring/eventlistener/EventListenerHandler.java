package com.cxg.study.spring.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Description: 事件监听处理器写法一 .
 * @Date: 2019/10/16 21:55
 * @Copyright: All rights reserved.
 */
@Slf4j
@Component
public class EventListenerHandler {

    @EventListener(OrderEvent.class)
    public void onOrderEvent(OrderEvent event) {
        OrderInfo orderInfo = (OrderInfo) event.getSource();
        log.info("orderInfo: {}", orderInfo);
        log.info("EventListenerHandler：{}", event.toString());
    }

    @EventListener(TestEvent.class)
    public void onTestEvent(TestEvent event) {
        TestInfo testInfo = (TestInfo) event.getSource();
        log.warn("testInfo: {}", testInfo);
        log.warn("TestEvent事件：{}", event.toString());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
//        BeanDefinitionEntity bean = event.getApplicationContext().getBean(BeanDefinitionEntity.class);
//        log.warn("ApplicationReadyEvent：{}", beanDefinitionEntity.hello("ApplicationReadyEvent"));
    }
}
