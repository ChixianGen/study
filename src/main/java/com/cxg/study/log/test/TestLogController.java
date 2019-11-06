package com.cxg.study.log.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cxg
 * @Date: 2019/10/19 19:05
 * @Copyright: All rights reserved.
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestLogController {

    @GetMapping
    public String testLogTest() {
        log.debug("==========test-debug==========");
        log.info("==========test-info==========");
        log.warn("==========test-warn==========");
        log.error("==========test-error==========");
        return "test";
    }
}
