package com.cxg.study.spring.eventlistener;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: cxg
 * @Date: 2019/10/16 21:49
 * @Copyright: All rights reserved.
 */
public class OrderEvent extends ApplicationEvent {

    public OrderEvent(OrderInfo source) {
        super(source);
    }
}
