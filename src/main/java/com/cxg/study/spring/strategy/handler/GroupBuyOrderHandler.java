package com.cxg.study.spring.strategy.handler;

import com.cxg.study.spring.strategy.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Description: 团购订单处理器 .
 * @Date: 2019/10/21 22:26
 * @Copyright: All rights reserved.
 */
@Component
public class GroupBuyOrderHandler extends AbstractHandler {
    @Override
    public String handleOrder(OrderDTO order) {
        return "GroupBuyOrderHandler";
    }
}
