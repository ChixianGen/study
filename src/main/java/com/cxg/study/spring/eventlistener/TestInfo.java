package com.cxg.study.spring.eventlistener;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: cxg
 * @Date: 2019/11/6 21:54
 * @Copyright: All rights reserved.
 */
@Data
@Builder
public class TestInfo {
    private Integer id;
    private String intro;
}
