package com.cxg.study.spring.eventlistener;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: cxg
 * @Date: 2019/11/6 21:54
 * @Copyright: All rights reserved.
 */
public class TestEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(Object source) {
        super(source);
    }
}
