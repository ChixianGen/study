package com.cxg.study.spring.strategy.handler;

import com.cxg.study.spring.strategy.OrderDTO;

/**
 * @Author: cxg
 * @Description: 抽象处理器，所有订单处理器的基类 .
 * @Date: 2019/10/21 22:02
 * @Copyright: All rights reserved.
 */
public abstract class AbstractHandler {
    public abstract String handleOrder(OrderDTO order);
}
