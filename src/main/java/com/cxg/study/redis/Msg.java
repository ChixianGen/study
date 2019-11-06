package com.cxg.study.redis;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: cxg
 * @Date: 2019/11/6 21:22
 * @Copyright: All rights reserved.
 */
@Data
public class Msg {
    private Long msgId;
    private String intro;
    private LocalDateTime version;
}
