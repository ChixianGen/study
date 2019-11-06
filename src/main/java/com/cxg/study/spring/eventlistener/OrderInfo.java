package com.cxg.study.spring.eventlistener;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: cxg
 * @Date: 2019/10/16 21:52
 * @Copyright: All rights reserved.
 */
@Data
@Builder
public class OrderInfo {
    private String orderNo;
    private Integer orderPrice;
    private String userId;
    private LocalDateTime createDate;
}
