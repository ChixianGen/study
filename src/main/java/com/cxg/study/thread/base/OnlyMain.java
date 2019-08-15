package com.cxg.study.thread.base;   // 16612 于 2019/8/12 创建;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;

/**
 * @ClassName: OnlyMain
 * @author: 16612
 * @date: 2019/8/12 20:47
 * @Copyright: All rights reserved.
 */
public class OnlyMain {
    public static void main(String args[]) {

        // JAVA虚拟机线程系统的管理接口；
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.printf("线程：【%s】\n", threadInfo.getThreadName());
        }
    }
}
