package com.cxg.study.spring.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author: cxg
 * @Date: 2019/10/16 21:58
 * @Copyright: All rights reserved.
 */
@RestController
@RequestMapping("event")
public class EventDemoController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("order")
    public OrderEvent event() {
        OrderInfo order = OrderInfo.builder()
                .userId("cxg")
                .orderPrice(888)
                .orderNo("DINGDAN001")
                .createDate(LocalDateTime.now())
                .build();
        OrderEvent orderEvent = new OrderEvent(order);
        applicationContext.publishEvent(orderEvent);
        return orderEvent;
    }

    @GetMapping("test")
    public TestEvent test() {
        TestInfo info = TestInfo.builder()
                .id(123)
                .intro("test_event_intro")
                .build();
        TestEvent testEvent = new TestEvent(info);
        applicationContext.publishEvent(testEvent);
        return testEvent;
    }
}
