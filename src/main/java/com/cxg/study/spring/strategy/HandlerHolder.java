package com.cxg.study.spring.strategy;

import com.cxg.study.spring.strategy.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: cxg
 * @Description: 订单处理器持有者 .
 * @Date: 2019/10/21 22:00
 * @Copyright: All rights reserved.
 */
@Component
public class HandlerHolder {

    /**
     * 所有handler集合
     * spring自动将所有【AbstractHandler】类型的实例注入进map集合中；
     */
    @Autowired
    private Map<String, AbstractHandler> handlerMap;

    /**
     * 获取处理器实例
     */
    public AbstractHandler getInstance(String type) {
        AbstractHandler handler = handlerMap.get(type);
        if (Objects.isNull(handler)) {
            throw new RuntimeException("订单处理类型不存在");
        }
        return handler;
    }
}
