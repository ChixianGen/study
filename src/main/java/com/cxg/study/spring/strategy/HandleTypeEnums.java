package com.cxg.study.spring.strategy;

/**
 * @Author: cxg
 * @Description: 处理类型枚举类，这里的name为处理器的实例名称 .
 * @Date: 2019/10/21 23:04
 * @Copyright: All rights reserved.
 */
public enum HandleTypeEnums {

    NORMAL("A", "normalOrderHandler"),
    GROUP_BUG("B", "groupBuyOrderHandler"),
    INTEGRAL("C", "integralOrderHandler"),
    ;

    private final String type;
    private final String name;

    HandleTypeEnums(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getImplInstance(String type) {
        HandleTypeEnums[] values = HandleTypeEnums.values();
        for (HandleTypeEnums e : values) {
            if (e.type.equals(type)) {
                return e.name;
            }
        }
        throw new RuntimeException("订单处理类型不存在");
    }
}
