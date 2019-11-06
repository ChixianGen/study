package com.cxg.study.spring.strategy;

import com.cxg.study.spring.strategy.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: cxg
 * @Description: 订单服务类 .
 * @Date: 2019/10/21 21:58
 * @Copyright: All rights reserved.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public String handleOrder(OrderDTO order) {
        AbstractHandler handler= handlerHolder.getInstance(HandleTypeEnums.getImplInstance(order.getType()));
        return handler.handleOrder(order);
    }
}
