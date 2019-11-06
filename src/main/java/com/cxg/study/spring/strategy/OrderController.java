package com.cxg.study.spring.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cxg
 * @Description: 订单访问控制器 .
 * @Date: 2019/10/21 23:15
 * @Copyright: All rights reserved.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("a")
    public String nomalOrder() {
        OrderDTO order = OrderDTO.builder().type("A").build();
        return orderService.handleOrder(order);
    }

    @GetMapping("b")
    public String groupBuyOrder() {
        OrderDTO order = OrderDTO.builder().type("B").build();
        return orderService.handleOrder(order);
    }

    @GetMapping("c")
    public String integralOrder() {
        OrderDTO order = OrderDTO.builder().type("C").build();
        return orderService.handleOrder(order);
    }

    @GetMapping("d")
    public String otherOrder() {
        OrderDTO order = OrderDTO.builder().type("D").build();
        return orderService.handleOrder(order);
    }
}
