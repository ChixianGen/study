package com.cxg.study.log.dev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cxg
 * @Date: 2019/10/19 19:03
 * @Copyright: All rights reserved.
 */
@Slf4j
@RestController
@RequestMapping("dev")
public class DevLogController {

    @GetMapping
    public String devLogTest() {
        log.debug("==========dev-debug==========");
        log.info("==========dev-info==========");
        log.warn("==========dev-warn==========");
        log.error("==========dev-error==========");
        return "dev";
    }
}
