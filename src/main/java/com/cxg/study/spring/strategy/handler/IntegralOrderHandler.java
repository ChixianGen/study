package com.cxg.study.spring.strategy.handler;

import com.cxg.study.spring.strategy.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Description: 积分订单处理器 .
 * @Date: 2019/10/21 22:27
 * @Copyright: All rights reserved.
 */
@Component
public class IntegralOrderHandler extends AbstractHandler {
    @Override
    public String handleOrder(OrderDTO order) {
        return "IntegralOrderHandler";
    }
}
