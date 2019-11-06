package com.cxg.study.spring.strategy;

/**
 * @Author: cxg
 * @Description: 订单服务类 .
 * @Date: 2019/10/21 21:44
 * @Copyright: All rights reserved.
 */
public interface IOrderService {

    /**
     * 根据订单的不同类型，使用不同的处理方式；
     * 只是模拟演示，简单的返回个字符串；
     */
    String handleOrder(OrderDTO order);
}

