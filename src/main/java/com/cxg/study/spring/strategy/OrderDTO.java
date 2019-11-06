package com.cxg.study.spring.strategy;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: cxg
 * @Description: 订单实体类 .
 * @Date: 2019/10/21 21:29
 * @Copyright: All rights reserved.
 */
@Data
@Builder
public class OrderDTO {
    /**
     * 订单类型
     * A. 普通订单
     * B. 团购订单
     * C. 积分订单
     * ...
     */
    private String type;
}
