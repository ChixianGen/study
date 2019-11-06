package com.cxg.study.spring.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Description: 事件监听处理器写法二 .
 * @Date: 2019/11/6 21:44
 * @Copyright: All rights reserved.
 */
@Slf4j
@Component
public class EventListenerHandlerOther implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(OrderEvent event) {
        log.warn("EventListenerHandlerOther: {}", event.toString());
    }
}
