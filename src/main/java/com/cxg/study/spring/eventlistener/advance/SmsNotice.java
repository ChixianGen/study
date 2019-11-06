package com.cxg.study.spring.eventlistener.advance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @Author: cxg
 * @Description: 短信通知 .
 * @Date: 2019/11/6 23:04
 * @Copyright: All rights reserved.
 */
@Slf4j
@Service("smsNotice")
public class SmsNotice implements Notice {

    public String noticed() {
        log.info("smsNotice");
        return "smsNotice";
    }

    @EventListener(value = OrderCreateEvent.class, condition = "#event.code.equals('sms')")
    public String onOrderCreateEvent(OrderCreateEvent event) {
        return noticed();
    }

    @Override
    public void notice() {

    }
}
