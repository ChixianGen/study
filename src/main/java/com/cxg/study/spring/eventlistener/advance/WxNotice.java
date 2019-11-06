package com.cxg.study.spring.eventlistener.advance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @Author: cxg
 * @Description: 微信通知 .
 * @Date: 2019/11/6 23:05
 * @Copyright: All rights reserved.
 */
@Slf4j
@Service("wxNotice")
public class WxNotice implements Notice {
    @Override
    public void notice() {
        log.info("wxNotice");
    }

    @EventListener(value = OrderCreateEvent.class, condition = "#event.code.equals('wx')")
    public void onOrderCreateEvent(OrderCreateEvent event) {
        notice();
    }
}
