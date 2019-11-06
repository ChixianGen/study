package com.cxg.study.spring.eventlistener.advance;

import com.cxg.study.spring.eventlistener.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author: cxg
 * @Date: 2019/11/6 23:33
 * @Copyright: All rights reserved.
 */
@RestController
@RequestMapping("event")
public class OrderCreateController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("create/{code}")
    public OrderCreateEvent test(@PathVariable String code) {
        OrderInfo order = OrderInfo.builder()
                .userId("cxg")
                .orderPrice(888)
                .orderNo("DINGDAN001")
                .createDate(LocalDateTime.now())
                .build();
        OrderCreateEvent orderEvent = new OrderCreateEvent(order, code);
        publisher.publishEvent(orderEvent);
        return orderEvent;
    }
}
