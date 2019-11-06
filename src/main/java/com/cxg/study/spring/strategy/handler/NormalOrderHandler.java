package com.cxg.study.spring.strategy.handler;

import com.cxg.study.spring.strategy.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: cxg
 * @Description: 普通的订单处理器 .
 * @Date: 2019/10/21 22:24
 * @Copyright: All rights reserved.
 */
@Component
public class NormalOrderHandler extends AbstractHandler{

    @Override
    public String handleOrder(OrderDTO order) {
        return "NormalOrderHandler";
    }
}
