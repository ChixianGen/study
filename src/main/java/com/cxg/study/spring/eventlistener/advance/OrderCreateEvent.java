package com.cxg.study.spring.eventlistener.advance;

import com.cxg.study.spring.eventlistener.OrderInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: cxg
 * @Description: 订单创建事件 .
 * @Date: 2019/11/6 23:24
 * @Copyright: All rights reserved.
 */
@Getter
public class OrderCreateEvent extends ApplicationEvent {

    private final String code;

    public OrderCreateEvent(OrderInfo source, String code) {
        super(source);
        this.code = code;
    }
}
