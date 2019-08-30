package com.cxg.study.utils;   // Administrator 于 2019/8/26 创建;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SleepUtils {

    /**
     * 模拟阻塞工作
     * @param timeout
     */
    public static void workingByBlock(long timeout) {
        long begin = System.currentTimeMillis();
        do {} while (System.currentTimeMillis() - begin < timeout);
    }

}
